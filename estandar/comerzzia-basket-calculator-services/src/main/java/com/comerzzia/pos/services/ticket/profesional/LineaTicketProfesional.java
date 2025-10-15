package com.comerzzia.pos.services.ticket.profesional;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.pos.services.core.sesion.Sesion;
import com.comerzzia.pos.services.core.sesion.SesionImpuestos;
import com.comerzzia.pos.services.ticket.lineas.LineaTicket;
import com.comerzzia.pos.services.ticket.promociones.IPromocionLineaTicket;
import com.comerzzia.pos.util.bigdecimal.BigDecimalUtil;
import com.comerzzia.pos.util.config.SpringContext;

@Component
@Scope("prototype")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "linea")
public class LineaTicketProfesional extends LineaTicket {

	/**
     * 
     */
	private static final long serialVersionUID = -7963112474025114042L;

	protected BigDecimal totalIva;
	protected BigDecimal totalRecargo;

	protected boolean precioModificado;

	public LineaTicketProfesional() {
		super();
		precioSinDto = null;
	}

	@Override
	public void recalcularPreciosImportes() {
		realizarCalculosDeLinea();
	}

	@Override
	public void recalcularImporteFinal() {
		realizarCalculosDeLinea();
	}

	protected void realizarCalculosDeLinea() {
		Long idTratamientoImpuestos = getCabecera().getTicket().getIdTratImpuestos();
		calcularDescuento();
		
		precioConDto = BigDecimalUtil.redondear(precioSinDto.subtract(precioSinDto.multiply(descuento).divide(new BigDecimal(100))), 4);
		descuento = BigDecimalUtil.redondear(descuento, 2);

		Sesion sesion = SpringContext.getBean(Sesion.class);
		SesionImpuestos sesionImpuestos = sesion.getImpuestos();
		importeConDto = BigDecimalUtil.redondear(precioConDto.multiply(cantidad), 2);
		impuestos = sesionImpuestos.getImpuestosProfesional(getCodImpuesto(), idTratamientoImpuestos, importeConDto);

		importeTotalConDto = BigDecimalUtil.redondear(importeConDto.add(impuestos), 2);

		precioTotalConDto = BigDecimalUtil.redondear(importeTotalConDto.divide(cantidad, RoundingMode.HALF_UP), 2);

		// Extraemos el total de iva y el total de recargo para su uso posterior en el cálculo de totales
		totalIva = BigDecimalUtil.redondear(sesionImpuestos.getPorcentajeIva(getCodImpuesto(), idTratamientoImpuestos, precioSinDto).multiply(cantidad),2);
		totalRecargo = BigDecimalUtil.redondear(sesionImpuestos.getPorcentajeRecargo(getCodImpuesto(), idTratamientoImpuestos, precioSinDto).multiply(cantidad),2);

		precioTotalSinDto = BigDecimalUtil.redondear(precioSinDto.add(sesionImpuestos.getImpuestosProfesional(getCodImpuesto(), idTratamientoImpuestos, precioSinDto)), 2);
		precioTotalTarifaOrigen = precioSinDto;
    }

	/**
	 * Calcula el descuento que se aplicará al calcular el precio con descuento de la línea.
	 */
	protected void calcularDescuento() {
	    // Limpiamos el descuento de la línea para su calculo posterior
		descuento = BigDecimal.ZERO;
		
		if(!tieneCambioPrecioManual() && !tieneDescuentoManual()) {
			precioSinDto = tarifa.getPrecioVenta();
			precioTarifaOrigen = tarifa.getPrecioVenta();
			// Limpiamos el descuento manual de operaciones anteriores
			descuentoManual = BigDecimal.ZERO;
		}
		
	    // Calculamos el descuento de la línea. Si no tiene cambio de precio manual ni descuento manu
		if(!tieneCambioPrecioManual() && !tieneDescuentoManual()) {
			descuento = BigDecimal.ZERO;
			importeTotalPromociones = BigDecimal.ZERO;
			importeTotalPromocionesMenosIngreso = BigDecimal.ZERO;
			for (IPromocionLineaTicket promocionLinea : promociones) {
				BigDecimal descuentoLinea = BigDecimal.ZERO;
				if(!BigDecimalUtil.isIgualACero(tarifa.getPrecioVenta())){
					 descuentoLinea = promocionLinea.getImporteTotalDtoMenosMargenSinRedondear().divide(tarifa.getPrecioVenta().multiply(cantidad), 15, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
				}
				descuento = descuento.add(descuentoLinea);
				
				// Rellenamos el resto de variables para la impresión en tickets.
				importeTotalPromociones = importeTotalPromociones.add(promocionLinea.getImporteTotalDtoMenosMargenSinRedondear());
				importeTotalPromocionesMenosIngreso = importeTotalPromocionesMenosIngreso.add(promocionLinea.getImporteTotalDtoMenosIngreso());
			}
		}
		// Si tiene descuento manual, el descuento será el descuento manual y no se aplican las promociones.
		else if (tieneDescuentoManual()) {
			descuento = descuentoManual;
		}
    }
	
	@Override
	public BigDecimal getPrecioAplicacionPromocion(){
		if (getPrecioPromocionSinDto()!=null){
			return getPrecioPromocionSinDto();
		}
		return getPrecioSinDto();
	}
	
	public BigDecimal getImporteAplicacionPromocionConDto(){
		return getImporteConDto();
	}

	public BigDecimal getTotalIva() {
		return totalIva;
	}

	public void setTotalIva(BigDecimal totalIva) {
		this.totalIva = totalIva;
	}

	public BigDecimal getTotalRecargo() {
		return totalRecargo;
	}

	public void setTotalRecargo(BigDecimal totalRecargo) {
		this.totalRecargo = totalRecargo;
	}

	public boolean isPrecioModificado() {
		return precioModificado;
	}

	public void setPrecioModificado(boolean precioModificado) {
		this.precioModificado = precioModificado;
	}
	
	@Override
	public boolean tieneCambioPrecioManual() {
		return precioSinDto != null && precioTarifaOrigen != null && !precioSinDto.equals(precioTarifaOrigen);
	}

	
	/* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.lineas.LineaTicket#isAplicaImpuestosPromociones()
	 */
	@Override
	public boolean isPrecioIncluyeImpuestos() {
		return false;
	}
	
}
