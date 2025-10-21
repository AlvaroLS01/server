package com.comerzzia.bricodepot.api.omnichannel.api.services.ventas.facturas.impresion;

import java.math.BigDecimal;

/**
 * Información de las promociones aplicadas en un ticket para su impresión.
 */
public class FacturaPromocion {

    private Long idPromocion;
    private String textoPromocion;
    private BigDecimal importeTotalAhorro;

    public Long getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(Long idPromocion) {
        this.idPromocion = idPromocion;
    }

    public String getTextoPromocion() {
        return textoPromocion;
    }

    public void setTextoPromocion(String textoPromocion) {
        this.textoPromocion = textoPromocion;
    }

    public BigDecimal getImporteTotalAhorro() {
        return importeTotalAhorro;
    }

    public void setImporteTotalAhorro(BigDecimal importeTotalAhorro) {
        this.importeTotalAhorro = importeTotalAhorro;
    }
}
