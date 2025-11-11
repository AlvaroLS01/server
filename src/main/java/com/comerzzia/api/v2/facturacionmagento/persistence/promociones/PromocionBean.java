package com.comerzzia.api.v2.facturacionmagento.persistence.promociones;

public class PromocionBean {

	private Long idPromocion;
	private Long idTipoPromocion;
	private Long tipoDto;
	private Boolean exclusiva;
	private String descripcion;

	public Long getIdPromocion() {
		return idPromocion;
	}

	public void setIdPromocion(Long idPromocion) {
		this.idPromocion = idPromocion;
	}

	public Long getIdTipoPromocion() {
		return idTipoPromocion;
	}

	public void setIdTipoPromocion(Long idTipoPromocion) {
		this.idTipoPromocion = idTipoPromocion;
	}

	public Long getTipoDto() {
		return tipoDto;
	}

	public void setTipoDto(Long tipoDto) {
		this.tipoDto = tipoDto;
	}

	public Boolean getExclusiva() {
		return exclusiva;
	}

	public void setExclusiva(Boolean exclusiva) {
		this.exclusiva = exclusiva;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
