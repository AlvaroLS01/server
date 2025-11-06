# BricoDepot Omnichannel API — Print Sales Document

## Endpoint
### `GET /salesdocument/{documentUid}/print`

Este endpoint permite **imprimir un documento de venta (ticket o factura)** a partir de su identificador único (`documentUid`).  
Forma parte de la **API Omnichannel de BricoDepot** y su función es generar el PDF de venta utilizando las mismas plantillas Jasper que el **backoffice**.

---

## Descripción general

El servicio realiza el siguiente flujo interno:

1. Busca el documento de venta correspondiente al `documentUid` recibido.
2. Localiza la plantilla Jasper asociada al tipo de documento (factura o devolución).
3. **Importante:**  
   Las plantillas **no se encuentran en los recursos desplegados del proyecto (`src/main/resources`)**,  
   sino que se **reutilizan las mismas plantillas del backoffice**, copiadas para pruebas en: src/main/test/resources/jasper/ventas/facturas/

Es decir, el módulo no mantiene sus propias plantillas compiladas; usa las del backoffice original.
4. Si solo existe el `.jrxml`, lo compila dinámicamente en tiempo de ejecución.
5. Carga la información fiscal (`ATCUD`, `QR_PORTUGAL`), las líneas del ticket, totales e IVA.
6. Genera el PDF final y lo devuelve codificado en Base64.

