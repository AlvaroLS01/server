package com.comerzzia.pos.services.ticket.lineas;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.pos.services.core.sesion.Sesion;
import com.comerzzia.pos.services.core.sesion.SesionImpuestos;
import com.comerzzia.pos.services.ticket.promociones.IPromocionLineaTicket;
import com.comerzzia.pos.util.bigdecimal.BigDecimalUtil;
import com.comerzzia.pos.util.format.FormatUtil;


@Component
@Scope("prototype")
public class LineaTicket extends LineaTicketAbstract implements ILineaTicket, Cloneable {
	@Autowired
	Sesion sesion;
	
    protected DocumentoOrigen documentoOrigen;

    protected List<String> numerosSerie;
    
   	protected Boolean updateStock;

	public LineaTicket() {
		super();
		
		updateStock = true;
	}		
  
    public void recalcularPreciosImportes() {
    	Long idTratamientoImpuestos = getCabecera().getTicket().getIdTratImpuestos();

        SesionImpuestos sesionImpuestos = sesion.getImpuestos();
    	
        // Calculamos precio con impuestos de la tarifa origen
        if (precioTarifaOrigen == null){
        	precioTarifaOrigen = tarifa.getPrecioVenta();
        }
        
        if(ivaIncluido){
        	precioTotalTarifaOrigen = tarifa.getPrecioTotal();
        	precioTarifaOrigen = sesionImpuestos.getPrecioVenta(getCodImpuesto(), idTratamientoImpuestos, precioTotalTarifaOrigen);
        }
        else{
        	impuestos = sesionImpuestos.getImpuestos(getCodImpuesto(), idTratamientoImpuestos, precioTarifaOrigen);
        	precioTotalTarifaOrigen = BigDecimalUtil.redondear(precioTarifaOrigen.add(impuestos));
        }        
        
        // Por defecto, el precio de venta sin descuentos es igual al precio de la tarifa origen
        precioSinDto = precioTarifaOrigen;
        precioTotalSinDto = precioTotalTarifaOrigen;
        
        recalcularImporteFinal();
    }

    
	public void recalcularImporteFinal() {
    	Long idTratamientoImpuestos = getCabecera().getTicket().getIdTratImpuestos();

    	importeTotalPromociones = BigDecimal.ZERO;
		importeTotalPromocionesMenosIngreso = BigDecimal.ZERO;
		BigDecimal importeTotalPromocionesSinRedondear = BigDecimal.ZERO;
		BigDecimal importeTotalConDtoSinRedondear = BigDecimal.ZERO;
		boolean cantidadTieneDecimales = !BigDecimalUtil.isIgualACero(cantidad.remainder(BigDecimal.ONE));
		BigDecimal importeLineaConImpuestos = BigDecimal.ZERO;
		
		if (tieneDescuentoManual()){
			importeTotalConDtoSinRedondear = BigDecimalUtil.menosPorcentajeR4(getPrecioTotalSinDto(), descuentoManual).multiply(cantidad);
			importeTotalConDto = BigDecimalUtil.redondear(importeTotalConDtoSinRedondear);
			importeLineaConImpuestos = importeTotalConDto;
		}
		else if (promociones != null) {
			for (IPromocionLineaTicket promocionLinea : promociones) {
				BigDecimal descuento = promocionLinea.getImporteTotalDtoMenosMargen();
				if (cantidadTieneDecimales) {
					//En lineas de peso (cantidad con decimales) usamos descuento sin redondear para no perder precisión en los siguientes cálculos
					descuento = promocionLinea.getImporteTotalDtoMenosMargenSinRedondear();
				}
				importeTotalPromociones = importeTotalPromociones.add(descuento);
				importeTotalPromocionesSinRedondear = importeTotalPromocionesSinRedondear.add(promocionLinea.getImporteTotalDtoMenosMargenSinRedondear());
				importeTotalPromocionesMenosIngreso = importeTotalPromocionesMenosIngreso.add(promocionLinea.getImporteTotalDtoMenosIngreso());
			}
			importeTotalConDto = BigDecimalUtil.redondear(getImporteTotalSinDtoSinRedondear().subtract(importeTotalPromociones));
			importeTotalConDtoSinRedondear = getImporteTotalSinDtoSinRedondear().subtract(importeTotalPromocionesSinRedondear);
			
			// Para calcular el precio con impuestos y el importe sin impuestos se mantiene toda la precisión de la operación de cantidad*preciototal-descuento
			importeLineaConImpuestos = getCantidad().multiply(precioTotalSinDto).subtract(importeTotalPromociones);
		}
		
		SesionImpuestos sesionImpuestos = sesion.getImpuestos();
		importeConDto = sesionImpuestos.getPrecioSinImpuestos(getCodImpuesto(), BigDecimalUtil.redondear(importeLineaConImpuestos), idTratamientoImpuestos);
		
		precioConDto = BigDecimalUtil.isIgualACero(cantidad) ? BigDecimal.ZERO : importeConDto.divide(cantidad, 4, RoundingMode.HALF_UP);
		precioTotalConDto = BigDecimalUtil.isIgualACero(cantidad) ? BigDecimal.ZERO : importeLineaConImpuestos.divide(cantidad, 2, RoundingMode.HALF_UP);
		descuento = BigDecimal.ZERO;
		if (tieneDescuentoManual()){
			descuento = descuentoManual;
		}
		else if(!promociones.isEmpty()) {
			// Calculamos el porcentaje de descuento desde el importe final sin redondear para que coincida con el porcentaje
			// exacto configurado en la promoción (por ejemplo, si configuro un 10% dto, que no salga un 9,99% tras los cÃ¡lculos)
			descuento = BigDecimalUtil.getTantoPorCientoMenos(getImporteTotalSinDtoSinRedondear(), importeTotalConDtoSinRedondear);

			// Confirmamos que el % descuento calculado, al aplicarlo sobre el precio unitario y multiplicarlo por la cantidad es igual
			// al importe final previamente calculado. En caso contrario, volveremos a calcular el % de descuento a partir del importe redondeado
			if (!BigDecimalUtil.isIgual(BigDecimalUtil.redondear(BigDecimalUtil.menosPorcentajeR(getPrecioTotalSinDto(), BigDecimalUtil.redondear(descuento)).multiply(cantidad)),(BigDecimalUtil.redondear(importeTotalConDto)))){
				descuento = BigDecimalUtil.getTantoPorCientoMenos(getImporteTotalSinDtoSinRedondear(), importeTotalConDto); 
			} 
		}
	}    
		
		
	public String getImporteTotalSinDtoAsString(){
		return FormatUtil.getInstance().formateaImporte(getImporteTotalSinDto());
	}
    

    public String getManualAsString() {
        return FormatUtil.getInstance().formateaImporte(descuentoManual);
    }

    
    public String getPrecioSinDtoAsString() {
        return FormatUtil.getInstance().formateaImporte(precioSinDto);
    }
    
    public String getPrecioTotalSinDtoAsString() {
        return FormatUtil.getInstance().formateaImporte(precioTotalSinDto);
    }
    
    public String getPrecioTotalConDtoAsString() {
        return FormatUtil.getInstance().formateaImporte(precioTotalConDto);
    }
    
    public String getPrecioConDtoAsString() {
        return FormatUtil.getInstance().formateaImporte(precioConDto);
    }
        
	public String getDescuentoFinalAsString() {
		String dtoFinal = "0";

		if (descuento != null) {
			dtoFinal = FormatUtil.getInstance().formateaNumero(descuento, 0);
		}
		return dtoFinal;
	}
    
	public String getDescuentoFinalConDecimalesAsString() {
		String dtoFinal = "0";

		if (descuento != null) {
			dtoFinal = FormatUtil.getInstance().formateaNumero(descuento, 2);
		}
		return dtoFinal;
	}
	
    public String getCantidadAsString(){
        return FormatUtil.getInstance().formateaNumero(getCantidad(), 3);
    }
	
	public String getImporteTotalConDtoAsString() {
		return FormatUtil.getInstance().formateaImporte(importeTotalConDto);
	}


	public DocumentoOrigen getDocumentoOrigen() {
		return documentoOrigen;
	}

	public void setDocumentoOrigen(DocumentoOrigen documentoOrigen) {
		this.documentoOrigen = documentoOrigen;
	}

		
	@Override
	public LineaTicket clone() {
		try {
			LineaTicket clone = this.getClass().newInstance();
			for (Class<?> obj = this.getClass(); !obj.equals(Object.class); obj = obj.getSuperclass()) {
				Field[] fields = obj.getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					fields[i].setAccessible(true);
					try{
						if(!Modifier.isFinal(fields[i].getModifiers())){
							fields[i].set(clone, fields[i].get(this));
						}
					}catch(IllegalAccessException e){
						log.warn("clone() - Error al clonar atributo de LineaTicket", e);
					}
				}
			}
			return clone;
		} catch (InstantiationException | IllegalAccessException e) {
			log.error("clone() - Error al clonar LineaTicket, lanzando RuntimeException", e);
			throw new RuntimeException("Error al clonar LineaTicket", e);
		}
	}
	
    public List<String> getNumerosSerie() {
    	return numerosSerie;
    }

    public void setNumerosSerie(List<String> numerosSerie) {
    	this.numerosSerie = numerosSerie;
    }	    	
	
	public Boolean isUpdateStock() {
		return updateStock;
	}

	public void setUpdateStock(Boolean updateStock) {
		this.updateStock = updateStock;
	}

	public String toString() {
		return "ID: " + idLinea + ". (" + articulo.getCodArticulo() + ") " + articulo.getDesArticulo() + ". " + getCantidadAsString() + " x " + getPrecioSinDtoAsString();
	}	
}
