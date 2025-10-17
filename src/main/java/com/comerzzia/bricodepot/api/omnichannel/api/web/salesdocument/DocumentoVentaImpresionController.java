package com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/salesdocument")
public class DocumentoVentaImpresionController {

        private final DocumentoVentaImpresionServicio servicioImpresion;

        @Autowired
        public DocumentoVentaImpresionController(DocumentoVentaImpresionServicio servicioImpresion) {
                this.servicioImpresion = servicioImpresion;
        }

        @GetMapping(value = "/{documentUid}/print")
        public ResponseEntity<byte[]> imprimir(@PathVariable("documentUid") String uidDocumento,
                @RequestParam(value = "mimeType", required = false, defaultValue = "application/pdf") String tipoMime,
                @RequestParam(value = "copy", required = false, defaultValue = "false") boolean esCopia,
                @RequestParam(value = "inline", required = false, defaultValue = "false") boolean enLinea,
                @RequestParam(value = "outputDocumentName", required = false) String nombreDocumentoSalida,
                @RequestParam(value = "printTemplate", required = false) String plantillaImpresion,
                @RequestParam Map<String, String> parametrosPeticion) {

                Map<String, String> parametrosPersonalizados = new HashMap<>(parametrosPeticion);
                parametrosPersonalizados.remove("mimeType");
                parametrosPersonalizados.remove("copy");
                parametrosPersonalizados.remove("inline");
                parametrosPersonalizados.remove("outputDocumentName");
                parametrosPersonalizados.remove("printTemplate");

                OpcionesImpresionDocumentoVenta opciones = new OpcionesImpresionDocumentoVenta(tipoMime, esCopia, enLinea,
                                nombreDocumentoSalida, plantillaImpresion, parametrosPersonalizados);

                Optional<DocumentoVentaImpresionResultado> respuesta = servicioImpresion.imprimir(uidDocumento, opciones);

                if (!respuesta.isPresent()) {
                        return ResponseEntity.noContent().build();
                }

                DocumentoVentaImpresionResultado documento = respuesta.get();
                HttpHeaders headers = new HttpHeaders();
                String mimeType = documento.getTipoMime();
                headers.setContentType(mimeType != null ? MediaType.parseMediaType(mimeType)
                                : MediaType.APPLICATION_OCTET_STREAM);
                byte[] contenido = documento.getContenido();
                headers.setContentLength(contenido.length);
                // Forzar la descarga del documento en el navegador independientemente del
                // parámetro inline recibido, tal y como se requiere para que el PDF
                // aparezca automáticamente en la bandeja de descargas.
                String dispositionType = "attachment";
                String nombreArchivo = documento.getNombreArchivo();
                if (nombreArchivo != null && !nombreArchivo.trim().isEmpty()) {
                        ContentDisposition disposition = ContentDisposition.builder(dispositionType)
                                        .filename(nombreArchivo, StandardCharsets.UTF_8)
                                        .build();
                        headers.setContentDisposition(disposition);
                } else {
                        headers.set(HttpHeaders.CONTENT_DISPOSITION, dispositionType);
                }

                return new ResponseEntity<>(contenido, headers, HttpStatus.OK);
        }
}
