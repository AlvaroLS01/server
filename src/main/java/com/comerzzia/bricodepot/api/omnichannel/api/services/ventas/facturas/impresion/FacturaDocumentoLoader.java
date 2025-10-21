package com.comerzzia.bricodepot.api.omnichannel.api.services.ventas.facturas.impresion;

import java.util.Optional;

import com.comerzzia.core.servicios.sesion.IDatosSesion;

/**
 * Carga la información necesaria para generar una factura a partir de un uid de documento.
 */
public interface FacturaDocumentoLoader {

    Optional<FacturaDocumento> load(String documentUid, IDatosSesion datosSesion);
}
