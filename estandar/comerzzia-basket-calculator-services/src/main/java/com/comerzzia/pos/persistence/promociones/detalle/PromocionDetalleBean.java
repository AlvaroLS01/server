package com.comerzzia.pos.persistence.promociones.detalle;

import java.math.BigDecimal;
import java.util.Date;

public class PromocionDetalleBean extends PromocionDetalleKey {
    private BigDecimal precioTarifa;

    private BigDecimal precioTarifaTotal;

    private BigDecimal precioVenta;

    private BigDecimal precioTotal;

    private Integer puntos;

    private String textoPromocion;

    private Date fechaInicio;

    private Date fechaFin;

    private Long versionTarifa;

    private Long idAgrupacion;

    private String agrupacion;

    private byte[] datosPromocion;
    
    //INICIO ATRIBUTOS PERSONALIZADOS--------------------------------------------
    
    //FIN ATRIBUTOS PERSONALIZADOS-----------------------------------------------


    public BigDecimal getPrecioTarifa() {
        return precioTarifa;
    }

    public void setPrecioTarifa(BigDecimal precioTarifa) {
        this.precioTarifa = precioTarifa;
    }

    public BigDecimal getPrecioTarifaTotal() {
        return precioTarifaTotal;
    }

    public void setPrecioTarifaTotal(BigDecimal precioTarifaTotal) {
        this.precioTarifaTotal = precioTarifaTotal;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public String getTextoPromocion() {
        return textoPromocion;
    }

    public void setTextoPromocion(String textoPromocion) {
        this.textoPromocion = textoPromocion == null ? null : textoPromocion.trim();
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

    public Long getVersionTarifa() {
        return versionTarifa;
    }

    public void setVersionTarifa(Long versionTarifa) {
        this.versionTarifa = versionTarifa;
    }

    public Long getIdAgrupacion() {
        return idAgrupacion;
    }

    public void setIdAgrupacion(Long idAgrupacion) {
        this.idAgrupacion = idAgrupacion;
    }

    public String getAgrupacion() {
        return agrupacion;
    }

    public void setAgrupacion(String agrupacion) {
        this.agrupacion = agrupacion == null ? null : agrupacion.trim();
    }

    public byte[] getDatosPromocion() {
        return datosPromocion;
    }

    public void setDatosPromocion(byte[] datosPromocion) {
        this.datosPromocion = datosPromocion;
    }
    
    //INICIO MÉTODOS PERSONALIZADOS--------------------------------------------
    
    //FIN MÉTODOS PERSONALIZADOS-----------------------------------------------

}