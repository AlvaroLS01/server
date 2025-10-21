package com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument;

import java.util.Optional;

public interface DocumentoVentaImpresionServicio {

        Optional<DocumentoVentaImpresionRespuesta> imprimir(String uidDocumento, OpcionesImpresionDocumentoVenta opciones);
}
