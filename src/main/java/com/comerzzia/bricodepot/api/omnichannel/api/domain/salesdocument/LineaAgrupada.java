package com.comerzzia.bricodepot.api.omnichannel.api.domain.salesdocument;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Value object that represents a grouped line in the invoice.
 */
public final class LineaAgrupada {

    private final String articulo;
    private final String descripcion;
    private final BigDecimal cantidad;
    private final BigDecimal precioUnitario;
    private final BigDecimal descuento;
    private final Map<String, Object> condicionesVenta;

    public LineaAgrupada(String articulo, String descripcion, BigDecimal cantidad,
            BigDecimal precioUnitario, BigDecimal descuento, Map<String, Object> condicionesVenta) {
        this.articulo = articulo;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.descuento = descuento;
        this.condicionesVenta = condicionesVenta;
    }

    public String getArticulo() {
        return articulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public Map<String, Object> getCondicionesVenta() {
        return condicionesVenta;
    }
}

