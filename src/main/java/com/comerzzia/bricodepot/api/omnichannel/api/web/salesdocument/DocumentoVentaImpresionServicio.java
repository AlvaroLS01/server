package com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument;

import java.util.Optional;

/**
 * Service contract used to generate the printable representation of a sales
 * document.
 */
public interface DocumentoVentaImpresionServicio {

    /**
     * Generates the printable representation for the provided sales document
     * identifier.
     *
     * @param uidDocumento the document identifier received in the API request
     * @param opciones     user supplied print options such as the desired mime
     *                     type, the target template or the activity UID to use as
     *                     context
     * @return the response to be sent back to the API consumer or an empty
     *         optional when the document cannot be printed
     */
    Optional<DocumentoVentaImpresionRespuesta> imprimir(String uidDocumento,
            OpcionesImpresionDocumentoVenta opciones);
}

