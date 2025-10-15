package com.comerzzia.pos.services.ticket.promociones;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.comerzzia.pos.services.promociones.Promocion;

public abstract class PromocionTicketAbstract implements IPromocionTicket {

    protected Long idPromocion;
    
    protected Long idTipoPromocion;

    protected Long tipoDescuento;

    protected Integer puntos;

    protected String textoPromocion;

    protected BigDecimal importeTotalAhorro;
    
    protected String futureDiscountType;
    
    protected String acceso;
    protected String codAcceso;
    protected Boolean exclusiva;
    protected String descripcionPromocion;
    
    protected Promocion promocion;

    private Map<String, Object> adicionales;
    
    
    public PromocionTicketAbstract() {
    }
    
    public PromocionTicketAbstract(Promocion promocion) {
    	this.promocion = promocion;
    	
    	idPromocion = promocion.getIdPromocion();
        importeTotalAhorro = BigDecimal.ZERO;
        idTipoPromocion = promocion.getIdTipoPromocion();
        tipoDescuento = promocion.getTipoDto();
        descripcionPromocion = promocion.getDescripcion();        
       
        textoPromocion = "";
        puntos = 0;
        exclusiva = promocion.getExclusiva();
        futureDiscountType = promocion.getFutureDiscountType();
    }
    
    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.promociones.IPromocionTicket#getImporteTotalAhorro()
	 */
    @Override
	public BigDecimal getImporteTotalAhorro() {
        return importeTotalAhorro;
    }
    
    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.promociones.IPromocionTicket#setImporteTotalAhorro(java.math.BigDecimal)
	 */
    @Override
	public void setImporteTotalAhorro(BigDecimal importeTotalAhorro) {
        this.importeTotalAhorro = importeTotalAhorro;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.promociones.IPromocionTicket#getIdPromocion()
	 */
    @Override
	public Long getIdPromocion() {
        return idPromocion;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.promociones.IPromocionTicket#setIdPromocion(java.lang.Long)
	 */
    @Override
	public void setIdPromocion(Long idPromocion) {
        this.idPromocion = idPromocion;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.promociones.IPromocionTicket#getIdTipoPromocion()
	 */
    @Override
	public Long getIdTipoPromocion() {
        return idTipoPromocion;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.promociones.IPromocionTicket#setIdTipoPromocion(java.lang.Long)
	 */
    @Override
	public void setIdTipoPromocion(Long idTipoPromocion) {
        this.idTipoPromocion = idTipoPromocion;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.promociones.IPromocionTicket#getTipoDescuento()
	 */
    @Override
	public Long getTipoDescuento() {
        return tipoDescuento;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.promociones.IPromocionTicket#setTipoDescuento(java.lang.Long)
	 */
    @Override
	public void setTipoDescuento(Long tipoDescuento) {
        this.tipoDescuento = tipoDescuento;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.promociones.IPromocionTicket#getPuntos()
	 */
    @Override
	public Integer getPuntos() {
        return puntos;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.promociones.IPromocionTicket#setPuntos(java.lang.Integer)
	 */
    @Override
	public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.promociones.IPromocionTicket#getTextoPromocion()
	 */
    @Override
	public String getTextoPromocion() {
    	if(textoPromocion == null || textoPromocion.isEmpty()){
    		return descripcionPromocion;
    	}
        return textoPromocion;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.promociones.IPromocionTicket#setTextoPromocion(java.lang.String)
	 */
    @Override
	public void setTextoPromocion(String textoPromocion) {
        this.textoPromocion = textoPromocion;
    }
    
    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.promociones.IPromocionTicket#getAcceso()
	 */
    @Override
	public String getAcceso() {
        return acceso;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.promociones.IPromocionTicket#setAcceso(java.lang.String)
	 */
    @Override
	public void setAcceso(String acceso) {
        this.acceso = acceso;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.promociones.IPromocionTicket#getCodAcceso()
	 */
    @Override
	public String getCodAcceso() {
        return codAcceso;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.promociones.IPromocionTicket#setCodAcceso(java.lang.String)
	 */
    @Override
	public void setCodAcceso(String codAcceso) {
        this.codAcceso = codAcceso;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.promociones.IPromocionTicket#isExclusiva()
	 */
    @Override
	public boolean isExclusiva() {
        return exclusiva;
    }
    
	/* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.promociones.IPromocionTicket#setExclusiva(boolean)
	 */
	@Override
	public void setExclusiva(boolean exclusiva) {
        this.exclusiva = exclusiva;
    }
    
    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.promociones.IPromocionTicket#isDescuentoMenosMargen()
	 */
    @Override
	public boolean isDescuentoMenosMargen(){
        return getTipoDescuento().equals(Promocion.TIPO_DTO_MENOS_MARGEN);
    }
    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.promociones.IPromocionTicket#isDescuentoMenosIngreso()
	 */
    @Override
	public boolean isDescuentoMenosIngreso(){
        return getTipoDescuento().equals(Promocion.TIPO_DTO_MENOS_INGRESO);
    }

	@Override
	public boolean isDescuentoAFuturo() {
		return getTipoDescuento().equals(Promocion.TIPO_DTO_A_FUTURO);
	}
	
    public Promocion getPromocion() {
		return promocion;
	}

	public void setPromocion(Promocion promocion) {
		this.promocion = promocion;
	}

	public Map<String, Object> getAdicionales() {
		return adicionales;
	}

	public void setAdicionales(Map<String, Object> adicionales) {
		this.adicionales = adicionales;
	}
	
	public void putAdicional(String key, Object value) {
		if(this.adicionales == null) {
			this.adicionales = new HashMap<String, Object>();
		}
		
		this.adicionales.put(key, value);
	}

	public String getFutureDiscountType() {
		return futureDiscountType;
	}

	public void setFutureDiscountType(String futureDiscountType) {
		this.futureDiscountType = futureDiscountType;
	}
	
	

	
}
