package com.comerzzia.pos.persistence.articulos.tarifas;

import java.math.BigDecimal;

public class TarifaDetalleBean extends TarifaDetalleKey {
    private BigDecimal precioCosto;

    private BigDecimal factorMarcaje;

    private BigDecimal precioVenta;

    private BigDecimal precioTotal;

    private Long version;

    private BigDecimal precioVentaRef;

    private BigDecimal precioVentaRefTotal;

    private String borrado;
    
    //INICIO ATRIBUTOS PERSONALIZADOS--------------------------------------------
    
    //FIN ATRIBUTOS PERSONALIZADOS-----------------------------------------------


    public BigDecimal getPrecioCosto() {
        return precioCosto;
    }

    public void setPrecioCosto(BigDecimal precioCosto) {
        this.precioCosto = precioCosto;
    }

    public BigDecimal getFactorMarcaje() {
        return factorMarcaje;
    }

    public void setFactorMarcaje(BigDecimal factorMarcaje) {
        this.factorMarcaje = factorMarcaje;
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public BigDecimal getPrecioVentaRef() {
        return precioVentaRef;
    }

    public void setPrecioVentaRef(BigDecimal precioVentaRef) {
        this.precioVentaRef = precioVentaRef;
    }

    public BigDecimal getPrecioVentaRefTotal() {
        return precioVentaRefTotal;
    }

    public void setPrecioVentaRefTotal(BigDecimal precioVentaRefTotal) {
        this.precioVentaRefTotal = precioVentaRefTotal;
    }

    public String getBorrado() {
        return borrado;
    }

    public void setBorrado(String borrado) {
        this.borrado = borrado == null ? null : borrado.trim();
    }
    
    //INICIO MÉTODOS PERSONALIZADOS--------------------------------------------
    
    //FIN MÉTODOS PERSONALIZADOS-----------------------------------------------

}