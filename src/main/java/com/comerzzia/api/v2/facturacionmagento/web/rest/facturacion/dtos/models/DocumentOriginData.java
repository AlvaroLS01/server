package com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Datos opcionales del documento origen cuando no se puede recuperar automáticamente del TPV")
public class DocumentOriginData {

        @Schema(description = "Serie del documento origen", example = "9001")
        private String serie;

        @Schema(description = "Código de caja que emitió el documento origen", example = "02")
        private String caja;

        @Schema(description = "Número de factura del documento origen", example = "438")
        private String numeroFactura;

        @Schema(description = "Identificador numérico del tipo de documento", example = "1")
        private String idTipoDocumento;

        @Schema(description = "Código del tipo de documento", example = "FS")
        private String codTipoDocumento;

        @Schema(description = "Descripción del tipo de documento", example = "FACTURA SIMPLIFICADA")
        private String desTipoDocumento;

        @Schema(description = "UID del ticket origen", example = "aa1731f6-c84c-4bf3-8fee-03c6648e18d1")
        private String uidTicket;

        @Schema(description = "Código completo del ticket origen", example = "FS 2025900102/00000438")
        private String codTicket;

        @Schema(description = "Indica si el ticket origen fue recuperado on-line", example = "true")
        private Boolean recoveredOnline;

        @Schema(description = "Fecha y hora del documento origen en formato ISO-8601", example = "2025-09-24T10:55:50+02:00")
        private String fecha;

        public String getSerie() {
                return serie;
        }

        public void setSerie(String serie) {
                this.serie = serie;
        }

        public String getCaja() {
                return caja;
        }

        public void setCaja(String caja) {
                this.caja = caja;
        }

        public String getNumeroFactura() {
                return numeroFactura;
        }

        public void setNumeroFactura(String numeroFactura) {
                this.numeroFactura = numeroFactura;
        }

        public String getIdTipoDocumento() {
                return idTipoDocumento;
        }

        public void setIdTipoDocumento(String idTipoDocumento) {
                this.idTipoDocumento = idTipoDocumento;
        }

        public String getCodTipoDocumento() {
                return codTipoDocumento;
        }

        public void setCodTipoDocumento(String codTipoDocumento) {
                this.codTipoDocumento = codTipoDocumento;
        }

        public String getDesTipoDocumento() {
                return desTipoDocumento;
        }

        public void setDesTipoDocumento(String desTipoDocumento) {
                this.desTipoDocumento = desTipoDocumento;
        }

        public String getUidTicket() {
                return uidTicket;
        }

        public void setUidTicket(String uidTicket) {
                this.uidTicket = uidTicket;
        }

        public String getCodTicket() {
                return codTicket;
        }

        public void setCodTicket(String codTicket) {
                this.codTicket = codTicket;
        }

        public Boolean getRecoveredOnline() {
                return recoveredOnline;
        }

        public void setRecoveredOnline(Boolean recoveredOnline) {
                this.recoveredOnline = recoveredOnline;
        }

        public String getFecha() {
                return fecha;
        }

        public void setFecha(String fecha) {
                this.fecha = fecha;
        }
}

