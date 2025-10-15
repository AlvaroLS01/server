package com.comerzzia.pos.services.ticket.profesional;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.pos.services.ticket.ITicket;
import com.comerzzia.pos.services.ticket.TicketVentaAbono;
import com.comerzzia.pos.services.ticket.cabecera.ISubtotalIvaTicket;
import com.comerzzia.pos.services.ticket.cabecera.TotalesTicket;
import com.comerzzia.pos.services.ticket.lineas.LineaTicketAbstract;
import com.comerzzia.pos.services.ticket.pagos.IPagoTicket;
import com.comerzzia.pos.util.bigdecimal.BigDecimalUtil;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "ivaTotal", "recargoTotal" })
@Component
@Scope("prototype")
public class TotalesTicketProfesional extends TotalesTicket {

	protected BigDecimal ivaTotal;
	protected BigDecimal recargoTotal;

	public TotalesTicketProfesional() {
		super();
	}

	@SuppressWarnings("rawtypes")
	public TotalesTicketProfesional(ITicket ticket) {
		resetearTotales();
		this.ticket = ticket;
	}

	@Override
	protected void resetearTotales() {
		super.resetearTotales();
		ivaTotal = BigDecimal.ZERO;
		recargoTotal = BigDecimal.ZERO;
	}

	@SuppressWarnings("unchecked")
    @Override
	public void recalcular() {
		resetearTotales();
		((TicketVentaProfesional) ticket).recalcularSubtotalesIva();

		for (ISubtotalIvaTicket subtotal : (List<ISubtotalIvaTicket>) ticket.getCabecera().getSubtotalesIva()) {
			ivaTotal = ivaTotal.add(subtotal.getCuota());
			recargoTotal = recargoTotal.add(subtotal.getCuotaRecargo());

			subtotal.setTotal(subtotal.getBase().add(subtotal.getCuota()).add(subtotal.getCuotaRecargo()));
			subtotal.setImpuestos(BigDecimalUtil.redondear(subtotal.getTotal().subtract(subtotal.getBase()), 2));
			subtotal.setTotal(BigDecimalUtil.redondear(subtotal.getTotal(), 2));
			
			base = base.add(subtotal.getBase());
		}

		total = base.add(ivaTotal).add(recargoTotal);
		impuestos = BigDecimalUtil.redondear(total.subtract(base), 2);
		total = BigDecimalUtil.redondear(total, 2);

		// Calcular el total a pagar (total sin promociones - total promociones tanto en línea como en cabecera)
		totalAPagar = total.subtract(totalPromocionesCabecera);

		// Calculamos entregado sumando todos los pagos
		for (Object pagoTicket : ((TicketVentaAbono) ticket).getPagos()) {
			entregado = entregado.add(((IPagoTicket) pagoTicket).getImporte());
		}

		// Calculamos el pendiente y el cambio
		BigDecimal totalEntregado = entregado.add(entregadoACuenta);
		if (BigDecimalUtil.isMenorACero(totalAPagar)) {
			if (BigDecimalUtil.isMayor(totalEntregado, totalAPagar)) {
				cambio.setImporte(BigDecimal.ZERO);
			}
			else {
				cambio.setImporte(totalEntregado.subtract(totalAPagar));
			}
		}
		else {
			if (BigDecimalUtil.isMenor(totalEntregado, totalAPagar)) {
				cambio.setImporte(BigDecimal.ZERO);
			}
			else {
				cambio.setImporte(totalEntregado.subtract(totalAPagar));
			}
		}
		
		// Calculamos los totales de las promociones
		for (LineaTicketAbstract lineaT :  (List<LineaTicketAbstract>)ticket.getLineas()) {
    		BigDecimal importeTotalPromociones = lineaT.getImporteTotalPromociones();
    		
    		BigDecimal importeTotalPromocionesMenosIngreso = lineaT.getImporteTotalPromocionesMenosIngreso();
			totalPromocionesCabecera = totalPromocionesCabecera.add(importeTotalPromocionesMenosIngreso);
			
    		totalPromocionesLineas = BigDecimalUtil.redondear(totalPromocionesLineas.add(importeTotalPromociones));
            //totalSinPromociones = BigDecimalUtil.redondear(totalSinPromociones.add(lineaT.getImporteTotalSinDto()));
            if(lineaT.getPromociones().isEmpty()) {
				totalSinPromociones = BigDecimalUtil.redondear(totalSinPromociones.add(lineaT.getImporteTotalConDto()));
			} else {
				totalSinPromociones = BigDecimalUtil.redondear(totalSinPromociones.add(lineaT.getImporteTotalSinDto()));
			}
        }
		totalPromociones = totalPromocionesCabecera.add(totalPromocionesLineas);
		totalPromocionesLineas = BigDecimalUtil.redondear(totalPromocionesLineas);
        totalPromociones = BigDecimalUtil.redondear(totalPromociones);
        totalPromocionesCabecera = BigDecimalUtil.redondear(totalPromocionesCabecera);
	}

	public BigDecimal getIvaTotal() {
		return ivaTotal;
	}

	public void setIvaTotal(BigDecimal ivaTotal) {
		this.ivaTotal = ivaTotal;
	}

	public BigDecimal getRecargoTotal() {
		return recargoTotal;
	}

	public void setRecargoTotal(BigDecimal recargoTotal) {
		this.recargoTotal = recargoTotal;
	}

}
