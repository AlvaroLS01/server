package com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.WrapDynaBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.jasperreports.engine.util.JRPropertiesUtil;

/**
 * Utility component that adapts the omnichannel data beans so that they expose
 * the legacy {@code codImp} fields expected by the backoffice Jasper
 * templates. The backoffice templates rely on MyBatis beans that publish the
 * tax percentage under {@code codImp}, whereas the omnichannel API exposes the
 * tax code using {@code codImpuesto}. By wrapping the affected collections with
 * {@link DynaBean} instances we can provide the additional property without
 * mutating the original JAXB-generated classes.
 */
final class JasperBeanEnhancer {

    static final String PROPERTY_USE_FIELD_DESCRIPTION =
            JRPropertiesUtil.PROPERTY_PREFIX + "javabean.use.field.description";

    private static final Logger LOGGER = LoggerFactory.getLogger(JasperBeanEnhancer.class);

    private JasperBeanEnhancer() {
    }

    @SuppressWarnings("unchecked")
    static void enhance(Map<String, Object> parameters) {
        if (parameters == null || parameters.isEmpty()) {
            return;
        }

        Object ticket = parameters.get("ticket");
        if (ticket == null) {
            return;
        }

        try {
            Map<String, String> taxPercentages = buildTaxPercentageIndex(ticket);

            enhanceTicketLines(ticket, taxPercentages);
            enhanceAggregatedLines(parameters, taxPercentages);
            enhanceTicketSubtotals(ticket, taxPercentages);
        }
        catch (Exception exception) {
            LOGGER.warn("enhance() - No fue posible adaptar los datos para las plantillas legacy", exception);
        }
    }

    private static void enhanceTicketLines(Object ticket, Map<String, String> taxPercentages) throws Exception {
        List<?> originalLines = getListProperty(ticket, "lineas");
        if (originalLines == null || originalLines.isEmpty()) {
            return;
        }

        try {
            @SuppressWarnings("unchecked")
            List<Object> mutableLines = (List<Object>) originalLines;
            for (int index = 0; index < mutableLines.size(); index++) {
                Object line = mutableLines.get(index);
                mutableLines.set(index, createAugmentedBean(line, determineCodImp(line, taxPercentages)));
            }
        }
        catch (UnsupportedOperationException exception) {
            List<Object> enhanced = new ArrayList<>(originalLines.size());
            for (Object line : originalLines) {
                enhanced.add(createAugmentedBean(line, determineCodImp(line, taxPercentages)));
            }
            PropertyUtils.setProperty(ticket, "lineas", enhanced);
        }
    }

    private static void enhanceAggregatedLines(Map<String, Object> parameters, Map<String, String> taxPercentages)
            throws Exception {
        Object groupedLines = parameters.get("lineasAgrupadas");
        if (!(groupedLines instanceof List<?> groupedList) || groupedList.isEmpty()) {
            return;
        }

        List<Object> enhanced = new ArrayList<>(groupedList.size());
        for (Object line : groupedList) {
            enhanced.add(createAugmentedBean(line, determineCodImp(line, taxPercentages)));
        }
        parameters.put("lineasAgrupadas", enhanced);
    }

    private static void enhanceTicketSubtotals(Object ticket, Map<String, String> taxPercentages) throws Exception {
        Object cabecera = PropertyUtils.getProperty(ticket, "cabecera");
        if (cabecera == null) {
            return;
        }

        List<?> subtotals = getListProperty(cabecera, "subtotalesIva");
        if (subtotals == null || subtotals.isEmpty()) {
            return;
        }

        try {
            @SuppressWarnings("unchecked")
            List<Object> mutableSubtotals = (List<Object>) subtotals;
            for (int index = 0; index < mutableSubtotals.size(); index++) {
                Object subtotal = mutableSubtotals.get(index);
                mutableSubtotals.set(index, createAugmentedBean(subtotal, determineCodImp(subtotal, taxPercentages)));
            }
        }
        catch (UnsupportedOperationException exception) {
            List<Object> enhanced = new ArrayList<>(subtotals.size());
            for (Object subtotal : subtotals) {
                enhanced.add(createAugmentedBean(subtotal, determineCodImp(subtotal, taxPercentages)));
            }
            PropertyUtils.setProperty(cabecera, "subtotalesIva", enhanced);
        }
    }

    private static Map<String, String> buildTaxPercentageIndex(Object ticket) throws Exception {
        Map<String, String> percentages = new HashMap<>();

        Object cabecera = PropertyUtils.getProperty(ticket, "cabecera");
        if (cabecera == null) {
            return percentages;
        }

        List<?> subtotals = getListProperty(cabecera, "subtotalesIva");
        if (subtotals == null) {
            return percentages;
        }

        for (Object subtotal : subtotals) {
            Object code = PropertyUtils.getProperty(subtotal, "codImpuesto");
            if (code != null) {
                percentages.put(code.toString(), resolvePercentage(subtotal));
            }
        }
        return percentages;
    }

    private static List<?> getListProperty(Object bean, String property) {
        try {
            Object value = PropertyUtils.getProperty(bean, property);
            if (value instanceof List<?>) {
                return (List<?>) value;
            }
        }
        catch (Exception exception) {
            LOGGER.debug("getListProperty() - No se pudo obtener la propiedad '{}' del bean '{}'", property, bean.getClass(),
                    exception);
        }
        return null;
    }

    private static Map<String, Object> determineCodImp(Object bean, Map<String, String> taxPercentages) throws Exception {
        String taxCode = null;
        try {
            Object code = PropertyUtils.getProperty(bean, "codImpuesto");
            if (code != null) {
                taxCode = code.toString();
            }
        }
        catch (NoSuchMethodException ignore) {
            // Some beans (e.g. subtotales) might not expose codImpuesto, rely on the index instead.
        }

        String percentage = taxCode == null ? null : taxPercentages.get(taxCode);
        if (percentage == null) {
            percentage = resolvePercentage(bean);
        }

        Map<String, Object> extra = new HashMap<>();
        if (percentage != null) {
            extra.put("codImp", percentage);
        }
        else if (taxCode != null) {
            extra.put("codImp", taxCode);
        }
        return extra;
    }

    private static String resolvePercentage(Object bean) {
        Object value;
        try {
            value = PropertyUtils.getProperty(bean, "porcentaje");
        }
        catch (Exception exception) {
            return null;
        }

        if (value instanceof BigDecimal decimal) {
            BigDecimal stripped = decimal.stripTrailingZeros();
            return stripped.toPlainString();
        }
        return Objects.toString(value, null);
    }

    private static DynaBean createAugmentedBean(Object delegate, Map<String, Object> extras) {
        if (extras.isEmpty()) {
            return new WrapDynaBean(delegate);
        }
        return new AugmentedWrapDynaBean(delegate, extras);
    }

    private static final class AugmentedWrapDynaBean extends WrapDynaBean {

        private final Map<String, Object> extras;

        private AugmentedWrapDynaBean(Object instance, Map<String, Object> extras) {
            super(instance);
            this.extras = extras;
        }

        @Override
        public Object get(String name) {
            if (extras.containsKey(name)) {
                return extras.get(name);
            }
            return super.get(name);
        }
    }
}
