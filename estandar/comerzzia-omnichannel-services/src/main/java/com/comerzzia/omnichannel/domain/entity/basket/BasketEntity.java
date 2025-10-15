package com.comerzzia.omnichannel.domain.entity.basket;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class BasketEntity extends BasketKey {
    private String codAlmacen;

    private String codcaja;

    private Long idTipoDocumento;

    private Date fechaCreacion;

    private Date fechaActualizacion;

    private String usuario;

    private String codCliente;

    private Long idFidelizado;

    private BigDecimal importe;

    private Integer numArticulos;

    private byte[] cesta;

}