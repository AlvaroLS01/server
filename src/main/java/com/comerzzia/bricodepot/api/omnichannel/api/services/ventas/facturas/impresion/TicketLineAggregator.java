package com.comerzzia.bricodepot.api.omnichannel.api.services.ventas.facturas.impresion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.comerzzia.core.util.numeros.BigDecimalUtil;
import com.comerzzia.omnichannel.documentos.facturas.converters.albaran.ticket.LineaTicket;
import com.comerzzia.omnichannel.documentos.facturas.converters.albaran.ticket.SubtotalIvaTicket;
import com.comerzzia.omnichannel.documentos.facturas.converters.albaran.ticket.TicketVentaAbono;

@Component
public class TicketLineAggregator {

    public List<LineaTicket> aggregate(TicketVentaAbono ticketVenta) {
        if (ticketVenta == null || ticketVenta.getLineas() == null) {
            return new ArrayList<>();
        }

        List<LineaTicket> lineas = ticketVenta.getLineas();
        List<LineaTicket> resultado = new ArrayList<>(lineas);
        List<Integer> lineasAgrupadas = new ArrayList<>();
        List<LineaTicket> copiaLineas = new ArrayList<>(lineas);

        for (LineaTicket linea : lineas) {
            if (lineasAgrupadas.contains(linea.getIdLinea())) {
                continue;
            }

            lineasAgrupadas.add(linea.getIdLinea());
            copiaLineas.remove(linea);

            BigDecimal cantidadTotal = linea.getCantidad();
            BigDecimal importeTotalPromociones = linea.getImporteTotalPromociones();
            BigDecimal importeTotalConDto = linea.getImporteTotalConDto();
            BigDecimal importeConDto = linea.getImporteConDto();

            Iterator<LineaTicket> iterator = copiaLineas.iterator();
            while (iterator.hasNext()) {
                LineaTicket candidata = iterator.next();
                if (!tienenMismasCondicionesVenta(linea, candidata)) {
                    continue;
                }

                iterator.remove();
                lineasAgrupadas.add(candidata.getIdLinea());
                resultado.remove(candidata);

                cantidadTotal = cantidadTotal.add(candidata.getCantidad());
                importeTotalPromociones = importeTotalPromociones.add(candidata.getImporteTotalPromociones());
                importeTotalConDto = importeTotalConDto.add(candidata.getImporteTotalConDto());
                importeConDto = importeConDto.add(candidata.getImporteConDto());
            }

            if (!BigDecimalUtil.isIgualACero(cantidadTotal)) {
                linea.setCantidad(cantidadTotal);
                linea.setImporteTotalPromociones(importeTotalPromociones);
                linea.setImporteTotalConDto(importeTotalConDto);
                linea.setImporteConDto(importeConDto);

                ajustarPorcentajeIva(ticketVenta, linea);
            }
        }

        return resultado;
    }

    private boolean tienenMismasCondicionesVenta(LineaTicket original, LineaTicket candidata) {
        if (!original.getCodArticulo().equals(candidata.getCodArticulo())) {
            return false;
        }
        if (!BigDecimalUtil.isIgual(original.getPrecioTotalConDto(), candidata.getPrecioTotalConDto())) {
            return false;
        }
        return original.getCantidad().signum() == candidata.getCantidad().signum();
    }

    private void ajustarPorcentajeIva(TicketVentaAbono ticketVenta, LineaTicket linea) {
        List<SubtotalIvaTicket> porcentajes = ticketVenta.getCabecera().getSubtotalesIva();
        if (porcentajes == null) {
            return;
        }

        for (SubtotalIvaTicket porcentaje : porcentajes) {
            if (porcentaje.getCodImpuesto().equals(linea.getCodImpuesto())) {
                linea.getIva().setPorcentaje(porcentaje.getPorcentaje());
                break;
            }
        }
    }
}
