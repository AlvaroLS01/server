package com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public final class TrabajoInformeBean {

    private final String informe;
    private final int reportVersion;
    private final ParametrosTrabajo parametros;

    private TrabajoInformeBean(Builder builder) {
        this.informe = builder.informe;
        this.reportVersion = builder.reportVersion;
        this.parametros = builder.parametros;
    }

    public String getInforme() {
        return informe;
    }

    public int getReportVersion() {
        return reportVersion;
    }

    public ParametrosTrabajo getParametros() {
        return parametros;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String informe;
        private int reportVersion;
        private ParametrosTrabajo parametros = new ParametrosTrabajo();

        private Builder() {
        }

        public Builder informe(String informe) {
            this.informe = informe;
            return this;
        }

        public Builder reportVersion(int reportVersion) {
            this.reportVersion = reportVersion;
            return this;
        }

        public Builder parametros(ParametrosTrabajo parametros) {
            this.parametros = parametros;
            return this;
        }

        public TrabajoInformeBean build() {
            Objects.requireNonNull(informe, "informe");
            return new TrabajoInformeBean(this);
        }
    }

    public static final class ParametrosTrabajo {
        private LocalDateTime fechaTicket;
        private String locatorId;
        private String uidInstancia;
        private byte[] logo;
        private String codigoTicket;
        private String pais;
        private String subreportDir;
        private boolean duplicado;
        private final Map<String, Object> parametrosAdicionales = new LinkedHashMap<>();

        public LocalDateTime getFechaTicket() {
            return fechaTicket;
        }

        public ParametrosTrabajo fechaTicket(LocalDateTime fechaTicket) {
            this.fechaTicket = fechaTicket;
            return this;
        }

        public String getLocatorId() {
            return locatorId;
        }

        public ParametrosTrabajo locatorId(String locatorId) {
            this.locatorId = locatorId;
            return this;
        }

        public String getUidInstancia() {
            return uidInstancia;
        }

        public ParametrosTrabajo uidInstancia(String uidInstancia) {
            this.uidInstancia = uidInstancia;
            return this;
        }

        public byte[] getLogo() {
            return logo;
        }

        public ParametrosTrabajo logo(byte[] logo) {
            this.logo = logo;
            return this;
        }

        public String getCodigoTicket() {
            return codigoTicket;
        }

        public ParametrosTrabajo codigoTicket(String codigoTicket) {
            this.codigoTicket = codigoTicket;
            return this;
        }

        public String getPais() {
            return pais;
        }

        public ParametrosTrabajo pais(String pais) {
            this.pais = pais;
            return this;
        }

        public String getSubreportDir() {
            return subreportDir;
        }

        public ParametrosTrabajo subreportDir(String subreportDir) {
            this.subreportDir = subreportDir;
            return this;
        }

        public boolean isDuplicado() {
            return duplicado;
        }

        public ParametrosTrabajo duplicado(boolean duplicado) {
            this.duplicado = duplicado;
            return this;
        }

        public Map<String, Object> getParametrosAdicionales() {
            return Collections.unmodifiableMap(parametrosAdicionales);
        }

        public ParametrosTrabajo parametro(String clave, Object valor) {
            if (valor != null) {
                parametrosAdicionales.put(clave, valor);
            }
            return this;
        }
    }
}

