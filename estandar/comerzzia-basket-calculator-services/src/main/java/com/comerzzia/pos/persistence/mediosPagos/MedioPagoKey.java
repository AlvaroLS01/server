package com.comerzzia.pos.persistence.mediosPagos;


public class MedioPagoKey {
    private String uidActividad;

    private String codMedioPago;

    public String getUidActividad() {
        return uidActividad;
    }

    public void setUidActividad(String uidActividad) {
        this.uidActividad = uidActividad == null ? null : uidActividad.trim();
    }

    public String getCodMedioPago() {
        return codMedioPago;
    }

    public void setCodMedioPago(String codMedioPago) {
        this.codMedioPago = codMedioPago == null ? null : codMedioPago.trim();
    }

    //INICIO MÉTODOS PERSONALIZADOS--------------------------------------------
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codMedioPago == null) ? 0 : codMedioPago.hashCode());
		result = prime * result + ((uidActividad == null) ? 0 : uidActividad.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MedioPagoKey other = (MedioPagoKey) obj;
		if (codMedioPago == null) {
			if (other.codMedioPago != null)
				return false;
		} else if (!codMedioPago.equals(other.codMedioPago))
			return false;
		if (uidActividad == null) {
			if (other.uidActividad != null)
				return false;
		} else if (!uidActividad.equals(other.uidActividad))
			return false;
		return true;
	}
    //FIN MÉTODOS PERSONALIZADOS-----------------------------------------------
    
}