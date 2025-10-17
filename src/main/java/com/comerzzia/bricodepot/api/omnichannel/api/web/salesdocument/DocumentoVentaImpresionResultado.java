package com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument;

import java.util.Arrays;

/**
 * Simple value object that represents the outcome of printing a sales document.
 */
public class DocumentoVentaImpresionResultado {

    private final String uidDocumento;
    private final boolean copia;
    private final boolean enLinea;
    private final String tipoMime;
    private final String nombreArchivo;
    private final byte[] contenido;

    public DocumentoVentaImpresionResultado(String uidDocumento, boolean copia, boolean enLinea, String tipoMime,
            String nombreArchivo, byte[] contenido) {
        this.uidDocumento = uidDocumento;
        this.copia = copia;
        this.enLinea = enLinea;
        this.tipoMime = tipoMime;
        this.nombreArchivo = nombreArchivo;
        this.contenido = contenido != null ? Arrays.copyOf(contenido, contenido.length) : new byte[0];
    }

    public String getUidDocumento() {
        return uidDocumento;
    }

    public boolean isCopia() {
        return copia;
    }

    public boolean isEnLinea() {
        return enLinea;
    }

    public String getTipoMime() {
        return tipoMime;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public byte[] getContenido() {
        return Arrays.copyOf(contenido, contenido.length);
    }
}
