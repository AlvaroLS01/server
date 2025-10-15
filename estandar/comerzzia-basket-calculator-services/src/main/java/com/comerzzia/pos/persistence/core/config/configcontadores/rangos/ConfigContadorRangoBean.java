package com.comerzzia.pos.persistence.core.config.configcontadores.rangos;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.comerzzia.core.basketcalculator.util.fechas.Fechas;
import com.comerzzia.pos.persistence.core.config.configcontadores.parametros.ConfigContadorParametroBean;
import com.comerzzia.pos.util.format.FormatUtil;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder={"rangoDescripcion","rangoFechaInicio","rangoFechaFin", "rangoInicio", "rangoFin", "rangoInicioFormateado", "rangoFinFormateado"})
public class ConfigContadorRangoBean extends ConfigContadorRangoKey {

	/**
     * 
     */
	private static final long serialVersionUID = 1572286506082423285L;

	public static final String VIGENCIA_CODEMP = "codEmp";
	public static final String VIGENCIA_CODALM = "codAlm";
	public static final String VIGENCIA_CODCAJA = "codCaja";

	@XmlElement(name="descripcion")
	private String rangoDescripcion;

	@XmlElement(name="inicio")
	private Long rangoInicio;

	@XmlElement(name="fin")
	private Long rangoFin;

	@XmlTransient
	private Long rangoAviso;

	@XmlTransient
	private Short rangoAvisoIntervalo;

	@XmlTransient
	private Long rangoUltimoAviso;

	@XmlElement(name="fecha_inicio")
	private Date rangoFechaInicio;

	@XmlElement(name="fecha_fin")
	private Date rangoFechaFin;

	@XmlTransient
	private Date rangoFechaAviso;

	@XmlTransient
	private Short rangoFechaAvisoIntervalo;

	@XmlTransient
	private Date rangoFechaUltimoAviso;

	@XmlTransient
	private String codemp;

	@XmlTransient
	private String codalm;

	@XmlTransient
	private String codcaja;
	
	// INICIO ATRIBUTOS PERSONALIZADOS--------------------------------------------
	
	@XmlElement(name="rangoInicioFormateado")
	private String rangoInicioFormateado;
	
	@XmlElement(name="rangoFinFormateado")
	private String rangoFinFormateado;

	// FIN ATRIBUTOS PERSONALIZADOS-----------------------------------------------

	public String getRangoDescripcion() {
		return rangoDescripcion;
	}

	public void setRangoDescripcion(String rangoDescripcion) {
		this.rangoDescripcion = rangoDescripcion == null ? null : rangoDescripcion.trim();
	}

	public Long getRangoInicio() {
		return rangoInicio;
	}

	public void setRangoInicio(Long rangoInicio) {
		this.rangoInicio = rangoInicio;
	}

	public Long getRangoFin() {
		return rangoFin;
	}

	public void setRangoFin(Long rangoFin) {
		this.rangoFin = rangoFin;
	}

	public Long getRangoAviso() {
		return rangoAviso;
	}

	public void setRangoAviso(Long rangoAviso) {
		this.rangoAviso = rangoAviso;
	}

	public Short getRangoAvisoIntervalo() {
		return rangoAvisoIntervalo;
	}

	public void setRangoAvisoIntervalo(Short rangoAvisoIntervalo) {
		this.rangoAvisoIntervalo = rangoAvisoIntervalo;
	}

	public Long getRangoUltimoAviso() {
		return rangoUltimoAviso;
	}

	public void setRangoUltimoAviso(Long rangoUltimoAviso) {
		this.rangoUltimoAviso = rangoUltimoAviso;
	}

	public Date getRangoFechaInicio() {
		return rangoFechaInicio;
	}

	public void setRangoFechaInicio(Date rangoFechaInicio) {
		this.rangoFechaInicio = rangoFechaInicio;
	}

	public Date getRangoFechaFin() {
		return rangoFechaFin;
	}

	public void setRangoFechaFin(Date rangoFechaFin) {
		this.rangoFechaFin = rangoFechaFin;
	}

	public Date getRangoFechaAviso() {
		return rangoFechaAviso;
	}

	public void setRangoFechaAviso(Date rangoFechaAviso) {
		this.rangoFechaAviso = rangoFechaAviso;
	}

	public Short getRangoFechaAvisoIntervalo() {
		return rangoFechaAvisoIntervalo;
	}

	public void setRangoFechaAvisoIntervalo(Short rangoFechaAvisoIntervalo) {
		this.rangoFechaAvisoIntervalo = rangoFechaAvisoIntervalo;
	}

	public Date getRangoFechaUltimoAviso() {
		return rangoFechaUltimoAviso;
	}

	public void setRangoFechaUltimoAviso(Date rangoFechaUltimoAviso) {
		this.rangoFechaUltimoAviso = rangoFechaUltimoAviso;
	}

	public String getCodemp() {
		return codemp;
	}

	public void setCodemp(String codemp) {
		this.codemp = codemp == null ? null : codemp.trim();
	}

	public String getCodalm() {
		return codalm;
	}

	public void setCodalm(String codalm) {
		this.codalm = codalm == null ? null : codalm.trim();
	}

	public String getCodcaja() {
		return codcaja;
	}

	public void setCodcaja(String codcaja) {
		this.codcaja = codcaja == null ? null : codcaja.trim();
	}

	// INICIO MÉTODOS PERSONALIZADOS--------------------------------------------
	public Date getProximoAvisoFecha() {
		Date res = null;
		if (this.getRangoFechaAviso() != null) {
			if (this.getRangoAvisoIntervalo() != null) {
				res = getAvisoFechaRecursivo(0);
			}
			else {
				res = this.getRangoFechaAviso();
			}

		}
		return res;
	}

	protected Date getAvisoFechaRecursivo(int iteracion) {
		Date res;
		Date proximoAviso = Fechas.sumaDias(this.getRangoFechaAviso(), this.getRangoFechaAvisoIntervalo() * iteracion);
		if (Fechas.equalsDate(proximoAviso, new Date()) || new Date().before(proximoAviso)) {
			res = proximoAviso;
		}
		else {
			res = getAvisoFechaRecursivo(iteracion + 1);
		}
		return res;
	}
	
	public String getRangoFechaInicioAsLocale(){
        
        Date fecha = getRangoFechaInicio();
        if(fecha==null){
            fecha = new Date();
        }
        String fechaAsLocale = FormatUtil.getInstance().formateaFechaCorta(fecha);
        return fechaAsLocale;
    }
	
	public String getRangoFechaFinAsLocale(){
        
        Date fecha = getRangoFechaFin();
        if(fecha==null){
            fecha = new Date();
        }
        String fechaAsLocale = FormatUtil.getInstance().formateaFechaCorta(fecha);
        return fechaAsLocale;
    }

	
    public String getRangoInicioFormateado() {
    	return rangoInicioFormateado;
    }

	
    public void setRangoInicioFormateado(String rangoInicioFormateado) {
    	this.rangoInicioFormateado = rangoInicioFormateado;
    }

	
    public String getRangoFinFormateado() {
    	return rangoFinFormateado;
    }
	
    public void setRangoFinFormateado(String rangoFinFormateado) {
    	this.rangoFinFormateado = rangoFinFormateado;
    }
    
    public void formatearRangos(String divisor, ConfigContadorParametroBean parametroRango, String separador){
    	rangoInicioFormateado = String.valueOf(rangoInicio);
    	rangoFinFormateado = String.valueOf(rangoFin);
        if(parametroRango!=null){        	
        	if(parametroRango.getDireccionRelleno() != null && !parametroRango.getDireccionRelleno().isEmpty()){
        		if("I".equals(parametroRango.getDireccionRelleno().toUpperCase())) {
        			while(rangoInicioFormateado.length() < parametroRango.getLongitudMaxima()) {
        				rangoInicioFormateado = parametroRango.getCaracterRelleno() + rangoInicioFormateado;
        			}
        			while(rangoFinFormateado.length() < parametroRango.getLongitudMaxima()) {
        				rangoFinFormateado = parametroRango.getCaracterRelleno() + rangoFinFormateado;
        			}
        		}
        		else{
        			while(rangoInicioFormateado.length() < parametroRango.getLongitudMaxima()) {
        				rangoInicioFormateado = rangoInicioFormateado + parametroRango.getCaracterRelleno();
        			}
        			while(rangoFinFormateado.length() < parametroRango.getLongitudMaxima()) {
        				rangoFinFormateado = rangoFinFormateado + parametroRango.getCaracterRelleno();
        			}
        		}
        	}
        }
        if("".equals(separador)){
			separador = "/";
		}
		rangoInicioFormateado= divisor+separador+rangoInicioFormateado;
		rangoFinFormateado=divisor+separador+rangoFinFormateado;
        
    }

	// FIN MÉTODOS PERSONALIZADOS-----------------------------------------------

}