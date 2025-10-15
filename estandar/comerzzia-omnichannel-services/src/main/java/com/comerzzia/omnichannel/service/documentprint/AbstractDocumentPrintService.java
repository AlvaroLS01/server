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

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
	protected final Logger log = Logger.getLogger(this.getClass());
	public static final String TEMPLATES_PATH = "doctemplates" + File.separator;

	@Autowired
	protected VariablesService variablesService;

	@Autowired
	EmpresasService empresasService;

	@Autowired
	protected PaisMapper paisMapper;

	protected Map<String, Object> generateCompanyDocParameters(IDatosSesion datosSesion, String companyCode) {
		Map<String, Object> companyParameters = new HashMap<String, Object>();

		// document params
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
		} catch (EmpresaException | EmpresaNotFoundException e) {
			throw new RuntimeException(e);
		}

		return companyParameters;
	}

	protected Map<String, Object> generateDocParameters(IDatosSesion datosSesion, PrintDocumentDTO printRequest)
			throws ApiException {
		log.debug("generateDocParameters() - Generanting docucment parameters for: " + printRequest.toString());
		Map<String, Object> docParameters = new HashMap<String, Object>();

		String companyCode = (String) printRequest.getCustomParams().get(DocumentPrintService.PARAM_COMPANY_CODE);

		if (!StringUtils.isBlank(companyCode)) {
			docParameters.putAll(generateCompanyDocParameters(datosSesion, companyCode));
		}

		// locale
		Locale locale = (Locale) printRequest.getCustomParams().get(DocumentPrintService.PARAM_LOCALE);

		if (locale == null) {
			locale = datosSesion.getLocale();
			docParameters.put(DocumentPrintService.PARAM_LOCALE, locale);
		}

		docParameters.put(DocumentPrintService.PARAM_COUNTRY_ID, locale.getCountry().toLowerCase());
		docParameters.put(DocumentPrintService.PARAM_LOCALE_ID, locale.getDisplayLanguage());

		// push custom request parameters
		docParameters.putAll(printRequest.getCustomParams());

		// currency
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

		// template file
		File templateFile = getTemplate(printRequest, docParameters);

		// change subreport dir to main report directory
		docParameters.put(DocumentPrintService.TEMPLATE_FILE, templateFile);
		docParameters.put("SUBREPORT_DIR", templateFile.getParent() + File.separator);
		docParameters.put("TEMPLATES_PATH", TEMPLATES_PATH);

		// global variables & session parameters
		docParameters.put(DocumentPrintService.PARAM_SESSION_DATA, datosSesion);

		try {
			docParameters.put("urlQR", variablesService.consultarValorCache(datosSesion, "TPV.URL_VISOR_DOCUMENTOS"));

			docParameters.put("COMBINATIONCODE1_TITLE",
					variablesService.consultarValorCache(datosSesion, "ARTICULOS.DESGLOSE1_TITULO"));
			docParameters.put("COMBINATIONCODE2_TITLE",
					variablesService.consultarValorCache(datosSesion, "ARTICULOS.DESGLOSE2_TITULO"));

			docParameters.put("TITULO_DESGLOSE1", docParameters.get("COMBINATIONCODE1_TITLE"));
			docParameters.put("TITULO_DESGLOSE2", docParameters.get("COMBINATIONCODE2_TITLE"));
		} catch (VariableException | VariableNotFoundException e) {
			throw new RuntimeException(e);
		}

		// format & i18n classes
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

	abstract protected String getTemplateExtension();

	protected File getTemplateLocaleFile(String template, String localeId) {
		String partialFileName = AppInfo.getInformesInfo().getRutaBase() + TEMPLATES_PATH + template;

		// by localeId
		File result = new File(partialFileName + "_" + localeId.toLowerCase() + getTemplateExtension());

		// by country
		if (!result.exists()) {
			result = new File(partialFileName + "_" + StringUtils.left(localeId.toLowerCase(), 2) + getTemplateExtension());
		}

		// by name
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
				log.debug("Find default print template: " + defaultTemplate);
				
				result = getTemplateLocaleFile(defaultTemplate, localeId);
			}
			
			if (!result.exists()) {
				throw new BadRequestException("Print template not found: " + result.getAbsolutePath());
			}
		}
		
		log.debug("getTemplate() - Using template: " + result.getAbsolutePath());

		return result;

	}

	public class EscapeTool {

		public String xml(Object string) {
			if (string == null)
				return null;
			return StringEscapeUtils.escapeXml(string.toString());
		}

	}

	public class TextUtils {
		/**
		 * Formatea un texto para dividirlos en lineas respecto a máximo recibido.
		 * 
		 * @param texto         : Texto que se desea dividir.
		 * @param maxCaracteres : Máximo de caracteres.
		 * @return
		 */
		public List<String> divideLines(String texto, int maxCaracteres) {
			return divideLines(texto, maxCaracteres, "");
		}

		public List<String> divideLines(String texto, int maxCaracteres, String separador) {
			List<String> lineas = new ArrayList<String>();

			if (StringUtils.isBlank(texto)) {
				return lineas;
			}

			if (StringUtils.isBlank(separador)) {
				separador = System.lineSeparator();
			}

			if (separador.equals("|")) {
				separador = "\\|";
			}

			String[] lineasIntroducidas = texto.split(separador);
			for (int i = 0; i < lineasIntroducidas.length; i++) {
				String lineaIntroducida = lineasIntroducidas[i];
				if (lineaIntroducida.length() <= maxCaracteres) {
					lineas.add(lineaIntroducida);
				} else {
					String[] palabrasLinea = lineaIntroducida.split(" ");
					String linea = "";
					for (int j = 0; j < palabrasLinea.length; j++) {
						String palabra = palabrasLinea[j];

						if (linea.length() + 1 + palabra.length() < maxCaracteres) {
							if (StringUtils.isNotBlank(linea)) {
								linea = linea + " " + palabra;
							} else {
								linea = palabra;
							}
						} else {
							lineas.add(linea);
							linea = palabra;
						}
						if (j == palabrasLinea.length - 1) {
							lineas.add(linea);
						}
					}
				}
			}

			return lineas;
		}

	}
}
