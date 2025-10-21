package com.comerzzia.bricodepot.api.persistence.pagos.mediospago;

public class DatosRespuestaPagoTarjetaDto {

        private String tarjeta;
        private String AID;
        private String tipoLectura;
        private String terminal;
        private String comercio;
        private String codAutorizacion;
        private String ARC;
        private String applicationLabel;
        private String marcaTarjeta;

        public String getTarjeta() {
                return tarjeta;
        }

        public void setTarjeta(String tarjeta) {
                this.tarjeta = tarjeta;
        }

        public String getAID() {
                return AID;
        }

        public void setAID(String aID) {
                AID = aID;
        }

        public String getTipoLectura() {
                return tipoLectura;
        }

        public void setTipoLectura(String tipoLectura) {
                this.tipoLectura = tipoLectura;
        }

        public String getTerminal() {
                return terminal;
        }

        public void setTerminal(String terminal) {
                this.terminal = terminal;
        }

        public String getComercio() {
                return comercio;
        }

        public void setComercio(String comercio) {
                this.comercio = comercio;
        }

        public String getCodAutorizacion() {
                return codAutorizacion;
        }

        public void setCodAutorizacion(String codAutorizacion) {
                this.codAutorizacion = codAutorizacion;
        }

        public String getARC() {
                return ARC;
        }

        public void setARC(String aRC) {
                ARC = aRC;
        }

        public String getApplicationLabel() {
                return applicationLabel;
        }

        public void setApplicationLabel(String applicationLabel) {
                this.applicationLabel = applicationLabel;
        }

        public String getMarcaTarjeta() {
                return marcaTarjeta;
        }

        public void setMarcaTarjeta(String marcaTarjeta) {
                this.marcaTarjeta = marcaTarjeta;
        }
}
