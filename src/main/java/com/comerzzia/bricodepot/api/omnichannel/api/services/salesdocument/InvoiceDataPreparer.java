package com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;

import com.comerzzia.bricodepot.api.omnichannel.api.domain.salesdocument.LineaAgrupada;
import com.comerzzia.bricodepot.api.omnichannel.api.domain.salesdocument.TicketVentaAbono.LineaTicket;
import com.comerzzia.bricodepot.api.omnichannel.api.domain.salesdocument.TicketVentaAbono.Pago;
import com.comerzzia.bricodepot.api.omnichannel.api.domain.salesdocument.TicketVentaAbono.TipoPago;
import com.comerzzia.bricodepot.api.omnichannel.api.domain.salesdocument.TicketVentaAbono.PromocionAplicada;

public class InvoiceDataPreparer {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceDataPreparer.class);

    public List<LineaAgrupada> agruparLineas(List<LineaTicket> lineas) {
        if (lineas == null || lineas.isEmpty()) {
            return new ArrayList<>();
        }

        Map<String, List<LineaTicket>> agrupadas = lineas.stream()
                .collect(Collectors.groupingBy(LineaTicket::groupingKey));

        return agrupadas.values().stream().map(lista -> {
            LineaTicket primera = lista.get(0);
            BigDecimal cantidad = lista.stream().map(LineaTicket::getCantidad).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal descuento = lista.stream().map(LineaTicket::getDescuento).reduce(BigDecimal.ZERO,
                    BigDecimal::add);
            BigDecimal precioUnitario = lista.isEmpty() ? BigDecimal.ZERO
                    : lista.get(0).getPrecioUnitario();
            return new LineaAgrupada(primera.getArticulo(), primera.getDescripcion(), cantidad,
                    precioUnitario, descuento, primera.getCondicionesVenta());
        }).collect(Collectors.toList());
    }

    public List<PromocionAplicada> promociones(List<PromocionAplicada> promociones) {
        if (promociones == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(promociones);
    }

    public List<Pago> pagosTarjeta(List<Pago> pagos) {
        if (pagos == null) {
            return new ArrayList<>();
        }
        return pagos.stream().filter(p -> p.getTipo() == TipoPago.TARJETA).collect(Collectors.toList());
    }

    public BigDecimal totalGiftCards(List<Pago> pagos) {
        if (pagos == null) {
            return BigDecimal.ZERO;
        }
        return pagos.stream().filter(p -> p.getTipo() == TipoPago.GIFT_CARD).map(Pago::getImporte)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String resumenGiftCard(List<Pago> pagos) {
        BigDecimal total = totalGiftCards(pagos);
        if (total.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }
        return "TOTAL_GIFTCARD=" + total.setScale(2, RoundingMode.HALF_UP);
    }

    public byte[] generarQr(String contenido) {
        if (contenido == null || contenido.isEmpty()) {
            return null;
        }
        QRCodeWriter writer = new QRCodeWriter();
        try {
            var matrix = writer.encode(contenido, BarcodeFormat.QR_CODE, 200, 200);
            BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                ImageIO.write(image, "png", out);
                return out.toByteArray();
            }
        } catch (WriterException e) {
            LOGGER.warn("Unable to encode QR", e);
            return null;
        } catch (Exception e) {
            LOGGER.warn("Unable to render QR image", e);
            return null;
        }
    }
}

