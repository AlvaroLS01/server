package com.comerzzia.core.basketcalculator.model.pasarelas;

import javax.xml.bind.annotation.XmlElement;

import com.comerzzia.core.basketcalculator.util.base.MantenimientoBean;

public class PasarelaConfig extends MantenimientoBean {

	private static final long serialVersionUID = -6890551038345597136L;

	@XmlElement(name = "tipo-moneda")
	private String tipoMoneda = "";
	@XmlElement(name = "url-pasarela-pago")
	private String urlPasarelaPago = "";
	private String clave = "";
	@XmlElement(name = "url-ok")
	private String urlOk = "";
	@XmlElement(name = "url-ko")
	private String urlKo = "";
	private String firma = "";

	public PasarelaConfig() {}

	public String getTipoMoneda() {
		return tipoMoneda;
	}

	public void setTipoMoneda(String tipoMoneda) {
		this.tipoMoneda = tipoMoneda;
	}

	public String getUrlPasarelaPago() {
		return urlPasarelaPago;
	}

	public void setUrlPasarelaPago(String urlPasarelaPago) {
		this.urlPasarelaPago = urlPasarelaPago;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getUrlOk() {
		return urlOk;
	}

	public void setUrlOk(String urlOk) {
		this.urlOk = urlOk;
	}

	public String getUrlKo() {
		return urlKo;
	}

	public void setUrlKo(String urlKo) {
		this.urlKo = urlKo;
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}
	
	@Override
	protected void initNuevoBean() {}

	@Override
	public String toString() {
		return "PasarelaBean [tipoMoneda=" + tipoMoneda + ", urlPasarelaPago=" + urlPasarelaPago + ", clave=" + clave + ", urlOk=" + urlOk + ", urlKo=" + urlKo + ", firma=" + firma + "]";
	}
}