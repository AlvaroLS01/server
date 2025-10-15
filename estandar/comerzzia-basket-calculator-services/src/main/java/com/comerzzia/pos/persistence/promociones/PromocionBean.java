package com.comerzzia.pos.persistence.promociones;

import java.util.Date;

import com.comerzzia.pos.persistence.promociones.tipos.PromocionTipoBean;

public class PromocionBean extends PromocionKey {

	private String codTarifa;

	private String descripcion;

	private Date fechaInicio;

	private Date fechaFin;

	private Boolean soloFidelizacion;

	private Long versionTarifa;

	private Long tipoDto;

	private String codColectivo;

	private Boolean exclusiva;

	private String codCupon;

	private String aplicaATarifas;

	private String codtarPrecios;
	
	protected String futureDiscountType;

	private byte[] datosPromocion;

	// INICIO ATRIBUTOS PERSONALIZADOS--------------------------------------------

	private String codAlmacen;

	private PromocionTipoBean promocionTipo = new PromocionTipoBean();

	// FIN ATRIBUTOS PERSONALIZADOS-----------------------------------------------

	public String getCodTarifa() {
		return codTarifa;
	}

	public void setCodTarifa(String codTarifa) {
		this.codTarifa = codTarifa == null ? null : codTarifa.trim();
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion == null ? null : descripcion.trim();
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Boolean getSoloFidelizacion() {
		return soloFidelizacion;
	}

	public void setSoloFidelizacion(Boolean soloFidelizacion) {
		this.soloFidelizacion = soloFidelizacion;
	}

	public Long getVersionTarifa() {
		return versionTarifa;
	}

	public void setVersionTarifa(Long versionTarifa) {
		this.versionTarifa = versionTarifa;
	}

	public Long getTipoDto() {
		return tipoDto;
	}

	public void setTipoDto(Long tipoDto) {
		this.tipoDto = tipoDto;
	}

	public String getCodColectivo() {
		return codColectivo;
	}

	public void setCodColectivo(String codColectivo) {
		this.codColectivo = codColectivo == null ? null : codColectivo.trim();
	}

	public Boolean getExclusiva() {
		return exclusiva;
	}

	public void setExclusiva(Boolean exclusiva) {
		this.exclusiva = exclusiva;
	}

	public String getCodCupon() {
		return codCupon;
	}

	public void setCodCupon(String codCupon) {
		this.codCupon = codCupon == null ? null : codCupon.trim();
	}

	public String getAplicaATarifas() {
		return aplicaATarifas;
	}

	public void setAplicaATarifas(String aplicaATarifas) {
		this.aplicaATarifas = aplicaATarifas;
	}

	public String getCodtarPrecios() {
		return codtarPrecios;
	}

	public void setCodtarPrecios(String codtarPrecios) {
		this.codtarPrecios = codtarPrecios == null ? null : codtarPrecios.trim();
	}

	public byte[] getDatosPromocion() {
		return datosPromocion;
	}

	public void setDatosPromocion(byte[] datosPromocion) {
		this.datosPromocion = datosPromocion;
	}

	// INICIO MÉTODOS PERSONALIZADOS--------------------------------------------

	@Override
	public String toString() {
		return getIdPromocion() + " - " + descripcion;
	}

	public Long getIdTipoPromocion() {
		return promocionTipo.getIdTipoPromocion();
	}

	public void setIdTipoPromocion(Long idTipoPromocion) {
		promocionTipo.setIdTipoPromocion(idTipoPromocion);
	}

	public String getDesTipoPromocion() {
		return promocionTipo.getDesTipoPromocion();
	}

	public void setDesTipoPromocion(String desTipoPromocion) {
		promocionTipo.setDesTipoPromocion(desTipoPromocion);
	}

	public byte[] getConfiguracion() {
		return promocionTipo.getConfiguracion();
	}

	public void setConfiguracion(byte[] configuracion) {
		promocionTipo.setConfiguracion(configuracion);
	}

	public PromocionTipoBean getTipoPromocion() {
		return promocionTipo;
	}

	public void setTipoPromocion(PromocionTipoBean tipoPromocion) {
		this.promocionTipo = tipoPromocion;
	}

	public String getCodAlmacen() {
		return codAlmacen;
	}

	public void setCodAlmacen(String codAlmacen) {
		this.codAlmacen = codAlmacen;
	}
	
	public String getFutureDiscountType() {
		return futureDiscountType;
	}

	public void setFutureDiscountType(String futureDiscountType) {
		this.futureDiscountType = futureDiscountType;
	}
	
	// FIN MÉTODOS PERSONALIZADOS-----------------------------------------------
}