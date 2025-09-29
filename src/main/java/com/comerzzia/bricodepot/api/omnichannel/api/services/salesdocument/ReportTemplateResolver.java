package com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Objects;

import com.comerzzia.bricodepot.api.omnichannel.api.domain.salesdocument.TicketVentaAbono;

public class ReportTemplateResolver {

    private final ReportProperties properties;

    public ReportTemplateResolver(ReportProperties properties) {
        this.properties = properties;
    }

    public TemplateResolution resolve(TicketVentaAbono ticket, String templateOverride) {
        String templateName;
        int reportVersion;

        if (templateOverride != null && !templateOverride.trim().isEmpty()) {
            templateName = templateOverride.trim();
            reportVersion = resolveVersion(templateName);
        } else {
            switch (ticket.getPais()) {
                case PT:
                    templateName = "facturaA4_PT";
                    reportVersion = 2;
                    break;
                case CA:
                    templateName = "facturaA4_CA";
                    reportVersion = 3;
                    break;
                default:
                    templateName = "facturaA4";
                    reportVersion = 1;
                    break;
            }
        }

        Path subreportDir = Paths.get(properties.getBasePath(), properties.getSubreportDirectory());
        return new TemplateResolution(templateName, reportVersion, subreportDir.toString());
    }

    private int resolveVersion(String templateName) {
        if (templateName == null) {
            return 1;
        }
        String normalized = templateName.toLowerCase(Locale.ROOT);
        if (normalized.contains("pt")) {
            return 2;
        }
        if (normalized.contains("ca")) {
            return 3;
        }
        return 1;
    }

    public static final class TemplateResolution {
        private final String templateName;
        private final int reportVersion;
        private final String subreportDirectory;

        public TemplateResolution(String templateName, int reportVersion, String subreportDirectory) {
            this.templateName = Objects.requireNonNull(templateName);
            this.reportVersion = reportVersion;
            this.subreportDirectory = Objects.requireNonNull(subreportDirectory);
        }

        public String getTemplateName() {
            return templateName;
        }

        public int getReportVersion() {
            return reportVersion;
        }

        public String getSubreportDirectory() {
            return subreportDirectory;
        }
    }
}

