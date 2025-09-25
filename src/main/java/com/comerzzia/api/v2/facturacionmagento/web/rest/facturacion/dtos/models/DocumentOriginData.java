package com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentOriginData {

        private String serie;
        private String caja;
        private String numeroFactura;
        private String idTipoDocumento;
        private String codTipoDocumento;
        private String desTipoDocumento;
        private String uidTicket;
        private String codTicket;
        private Boolean recoveredOnline;
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

