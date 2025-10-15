package com.comerzzia.pos.persistence.tarifas;

import java.util.Date;

public class TarifaBean extends TarifaKey {

	private static final long serialVersionUID = -8282995938657438384L;

	public static final String TARIFA_GENERAL = "GENERAL";
	
    private String desTarifa;

    private Long version;

    private Integer idGrupoImpuestos;

    private Long idTratImpuestos;

    private Date fechaVersion;

    private String codTarifaPadre;
    
    private String precioConImpuestos;
    
    //INICIO ATRIBUTOS PERSONALIZADOS--------------------------------------------
    
    //FIN ATRIBUTOS PERSONALIZADOS-----------------------------------------------


    public String getDesTarifa() {
        return desTarifa;
    }

    public void setDesTarifa(String desTarifa) {
        this.desTarifa = desTarifa == null ? null : desTarifa.trim();
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Integer getIdGrupoImpuestos() {
        return idGrupoImpuestos;
    }

    public void setIdGrupoImpuestos(Integer idGrupoImpuestos) {
        this.idGrupoImpuestos = idGrupoImpuestos;
    }

    public Long getIdTratImpuestos() {
        return idTratImpuestos;
    }

    public void setIdTratImpuestos(Long idTratImpuestos) {
        this.idTratImpuestos = idTratImpuestos;
    }

    public Date getFechaVersion() {
        return fechaVersion;
    }

    public void setFechaVersion(Date fechaVersion) {
        this.fechaVersion = fechaVersion;
    }

    public void setCodTarifaPadre(String codTarifaPadre) {
        this.codTarifaPadre = codTarifaPadre == null ? null : codTarifaPadre.trim();
    }
    
    public String getPrecioConImpuestos() {
    	return precioConImpuestos;
    }
	
    public void setPrecioConImpuestos(String precioConImpuestos) {
    	this.precioConImpuestos = precioConImpuestos;
    }
    
    //INICIO MÉTODOS PERSONALIZADOS--------------------------------------------
    
    public String getCodTarifaGeneral() {
		return TARIFA_GENERAL;
	}
	
	public String getCodTarifaPadre() {
		if(this.codTarifaPadre != null && !this.codTarifaPadre.isEmpty()) {
			return codTarifaPadre;
		}
		return TARIFA_GENERAL;
	}

//	public boolean isPrecioConImpuestos() {
//	    return precioConImpuestos != null && precioConImpuestos.equals("S");
//    }
	
    //FIN MÉTODOS PERSONALIZADOS-----------------------------------------------

}