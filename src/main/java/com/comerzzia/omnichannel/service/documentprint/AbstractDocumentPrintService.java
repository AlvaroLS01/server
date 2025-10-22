package com.comerzzia.omnichannel.service.documentprint;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.api.core.service.exception.BadRequestException;
import com.comerzzia.core.model.empresas.EmpresaBean;
import com.comerzzia.core.servicios.empresas.EmpresaException;
import com.comerzzia.core.servicios.empresas.EmpresaNotFoundException;
import com.comerzzia.core.servicios.empresas.EmpresasService;
import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.core.servicios.variables.VariableException;
import com.comerzzia.core.servicios.variables.VariableNotFoundException;
import com.comerzzia.core.servicios.variables.VariablesService;
import com.comerzzia.core.util.base64.Base64Coder;
import com.comerzzia.core.util.config.AppInfo;
import com.comerzzia.omnichannel.domain.dto.saledoc.PrintDocumentDTO;
import com.comerzzia.omnichannel.model.documents.util.FormatUtil;
import com.comerzzia.pos.persistence.paises.PaisBean;
import com.comerzzia.pos.persistence.paises.PaisKey;
import com.comerzzia.pos.persistence.paises.PaisMapper;

public abstract class AbstractDocumentPrintService {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    public static final String TEMPLATES_PATH = "doctemplates" + File.separator;

    @Autowired
    protected VariablesService variablesService;

    @Autowired
    protected EmpresasService empresasService;

    @Autowired
    protected PaisMapper paisMapper;

    protected Map<String, Object> generateCompanyDocParameters(IDatosSesion datosSesion, String companyCode) {
        Map<String, Object> companyParameters = new HashMap<>();

        try {
            EmpresaBean empresa = empresasService.consultar(datosSesion, companyCode);

            if (empresa.getLogotipo() != null && empresa.getLogotipo().length > 0) {
                companyParameters.put(DocumentPrintService.PARAM_LOGO, new ByteArrayInputStream(empresa.getLogotipo()));
            }

            PaisKey paisKey = new PaisKey();
            paisKey.setUidInstancia(datosSesion.getUidInstancia());
            paisKey.setCodPais(empresa.getCodPais());

            PaisBean paisBean = paisMapper.selectByPrimaryKey(paisKey);

            companyParameters.put(DocumentPrintService.PARAM_CURRENCY_CODE, paisBean.getCodDivisa());
        }
        catch (EmpresaException | EmpresaNotFoundException e) {
            throw new RuntimeException(e);
        }

        return companyParameters;
    }

    protected Map<String, Object> generateDocParameters(IDatosSesion datosSesion, PrintDocumentDTO printRequest)
            throws ApiException {
        log.debug("generateDocParameters() - Generating document parameters for: {}", printRequest);
        Map<String, Object> docParameters = new HashMap<>();

        String companyCode = (String) printRequest.getCustomParams().get(DocumentPrintService.PARAM_COMPANY_CODE);

        if (!StringUtils.isBlank(companyCode)) {
            docParameters.putAll(generateCompanyDocParameters(datosSesion, companyCode));
        }

        Locale locale = (Locale) printRequest.getCustomParams().get(DocumentPrintService.PARAM_LOCALE);

        if (locale == null) {
            locale = datosSesion.getLocale();
            docParameters.put(DocumentPrintService.PARAM_LOCALE, locale);
        }

        docParameters.put(DocumentPrintService.PARAM_COUNTRY_ID, locale.getCountry().toLowerCase(Locale.ROOT));
        docParameters.put(DocumentPrintService.PARAM_LOCALE_ID, locale.getDisplayLanguage());

        docParameters.putAll(printRequest.getCustomParams());

        String currencyCode = (String) docParameters.get(DocumentPrintService.PARAM_CURRENCY_CODE);

        if (StringUtils.isBlank(currencyCode)) {
            currencyCode = Currency.getInstance(locale).getCurrencyCode();
            docParameters.put(DocumentPrintService.PARAM_CURRENCY_CODE, currencyCode);
        }

        docParameters.put("MONEDA_PAIS", currencyCode);

        docParameters.put("salida", (printRequest.getScreenOutput() ? "pantalla" : "impresora"));
        docParameters.put("output", (printRequest.getScreenOutput() ? "screen" : "printer"));

        docParameters.put("BASE_DIR", AppInfo.getInformesInfo().getRutaBase());
        docParameters.put("REPORT_LOCALE", locale);

        File templateFile = getTemplate(printRequest, docParameters);

        docParameters.put(DocumentPrintService.TEMPLATE_FILE, templateFile);
        docParameters.put("SUBREPORT_DIR", templateFile.getParent() + File.separator);
        docParameters.put("TEMPLATES_PATH", TEMPLATES_PATH);

        docParameters.put(DocumentPrintService.PARAM_SESSION_DATA, datosSesion);

        try {
            docParameters.put("urlQR", variablesService.consultarValorCache(datosSesion, "TPV.URL_VISOR_DOCUMENTOS"));

            docParameters.put("COMBINATIONCODE1_TITLE",
                    variablesService.consultarValorCache(datosSesion, "ARTICULOS.DESGLOSE1_TITULO"));
            docParameters.put("COMBINATIONCODE2_TITLE",
                    variablesService.consultarValorCache(datosSesion, "ARTICULOS.DESGLOSE2_TITULO"));

            docParameters.put("TITULO_DESGLOSE1", docParameters.get("COMBINATIONCODE1_TITLE"));
            docParameters.put("TITULO_DESGLOSE2", docParameters.get("COMBINATIONCODE2_TITLE"));
        }
        catch (VariableException | VariableNotFoundException e) {
            throw new RuntimeException(e);
        }

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        Currency currency = Currency.getInstance(currencyCode);
        numberFormat.setCurrency(currency);
        numberFormat.setMaximumFractionDigits(currency.getDefaultFractionDigits());
        docParameters.put("CURRENCY_FORMATTER", numberFormat);

        NumberFormat numberFormatSinDecimales = NumberFormat.getInstance(locale);
        numberFormatSinDecimales.setGroupingUsed(false);
        numberFormatSinDecimales.setMinimumFractionDigits(0);
        numberFormatSinDecimales.setMaximumFractionDigits(0);
        docParameters.put("NUMBER_FORMATTER_SIN_DECIMALES", numberFormatSinDecimales);
        docParameters.put("NUMBER_FORMATTER_NO_DECIMALS", numberFormatSinDecimales);

        NumberFormat numberFormat3Decimales = NumberFormat.getInstance(locale);
        numberFormat3Decimales.setGroupingUsed(true);
        numberFormat3Decimales.setMinimumFractionDigits(3);
        numberFormat3Decimales.setMaximumFractionDigits(3);
        docParameters.put("NUMBER_FORMATTER_3_DECIMALES", numberFormat3Decimales);
        docParameters.put("NUMBER_FORMATTER_3_DECIMALS", numberFormat3Decimales);

        FormatUtil fmt = new FormatUtil(locale);

        docParameters.put("fmt", fmt);
        docParameters.put("esc", new EscapeTool());
        docParameters.put("textUtils", new TextUtils());
        docParameters.put("base64Coder", new Base64Coder(Base64Coder.UTF8));
        docParameters.put("i18n", new LanguageUtilForLocale(locale));

        return docParameters;
    }

    protected abstract String getTemplateExtension();

    protected File getTemplateLocaleFile(String template, String localeId) {
        String partialFileName = AppInfo.getInformesInfo().getRutaBase() + TEMPLATES_PATH + template;

        File result = new File(partialFileName + "_" + localeId.toLowerCase(Locale.ROOT) + getTemplateExtension());

        if (!result.exists()) {
            result = new File(partialFileName + "_" + StringUtils.left(localeId.toLowerCase(Locale.ROOT), 2)
                    + getTemplateExtension());
        }

        if (!result.exists()) {
            result = new File(partialFileName + getTemplateExtension());
        }

        return result;
    }

    protected File getTemplate(PrintDocumentDTO printRequest, Map<String, Object> docParameters) throws ApiException {
        String templateExtension = getTemplateExtension();

        Assert.notNull(templateExtension, "Template extension not defined");

        String template = printRequest.getPrintTemplate();
        String defaultTemplate = (String) docParameters.get(DocumentPrintService.PARAM_DEFAULT_TEMPLATE);

        if (StringUtils.isEmpty(template) && StringUtils.isEmpty(defaultTemplate)) {
            throw new BadRequestException("Print template not defined");
        }

        if (StringUtils.isEmpty(template)) {
            template = defaultTemplate;
        }

        String localeId = (String) docParameters.get(DocumentPrintService.PARAM_LOCALE_ID);

        File result = getTemplateLocaleFile(template, localeId);

        if (!result.exists()) {
            if (!StringUtils.equals(template, defaultTemplate)) {
                log.debug("Find default print template: {}", defaultTemplate);

                result = getTemplateLocaleFile(defaultTemplate, localeId);
            }

            if (!result.exists()) {
                throw new BadRequestException("Print template not found: " + result.getAbsolutePath());
            }
        }

        log.debug("getTemplate() - Using template: {}", result.getAbsolutePath());

        return result;
    }

    public static class EscapeTool {

        public String xml(Object value) {
            if (value == null) {
                return null;
            }
            String input = value.toString();
            StringBuilder builder = new StringBuilder(input.length());
            for (int i = 0; i < input.length(); i++) {
                char ch = input.charAt(i);
                switch (ch) {
                    case '&':
                        builder.append("&amp;");
                        break;
                    case '<':
                        builder.append("&lt;");
                        break;
                    case '>':
                        builder.append("&gt;");
                        break;
                    case '"':
                        builder.append("&quot;");
                        break;
                    case '\'':
                        builder.append("&apos;");
                        break;
                    default:
                        builder.append(ch);
                        break;
                }
            }
            return builder.toString();
        }
    }

    public static class TextUtils {

        public List<String> divideLines(String texto, int maxCaracteres) {
            return divideLines(texto, maxCaracteres, "");
        }

        public List<String> divideLines(String texto, int maxCaracteres, String separador) {
            List<String> lineas = new ArrayList<>();

            if (StringUtils.isBlank(texto)) {
                return lineas;
            }

            String delimiter = separador;
            if (StringUtils.isBlank(delimiter)) {
                delimiter = System.lineSeparator();
            }

            if ("|".equals(delimiter)) {
                delimiter = "\\|";
            }

            String[] lineasIntroducidas = texto.split(delimiter);
            for (String lineaIntroducida : lineasIntroducidas) {
                if (lineaIntroducida.length() <= maxCaracteres) {
                    lineas.add(lineaIntroducida);
                }
                else {
                    String[] palabrasLinea = lineaIntroducida.split(" ");
                    StringBuilder linea = new StringBuilder();
                    for (int j = 0; j < palabrasLinea.length; j++) {
                        String palabra = palabrasLinea[j];

                        if (linea.length() == 0) {
                            linea.append(palabra);
                        }
                        else if (linea.length() + 1 + palabra.length() < maxCaracteres) {
                            linea.append(' ').append(palabra);
                        }
                        else {
                            lineas.add(linea.toString());
                            linea.setLength(0);
                            linea.append(palabra);
                        }

                        if (j == palabrasLinea.length - 1) {
                            lineas.add(linea.toString());
                        }
                    }
                }
            }

            return lineas;
        }
    }
}
