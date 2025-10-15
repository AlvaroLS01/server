/**
 * ComerZZia 3.0
 *
 * Copyright (c) 2008-2015 Comerzzia, S.L.  All Rights Reserved.
 *
 * THIS WORK IS  SUBJECT  TO  SPAIN  AND  INTERNATIONAL  COPYRIGHT  LAWS  AND
 * TREATIES.   NO  PART  OF  THIS  WORK MAY BE  USED,  PRACTICED,  PERFORMED
 * COPIED, DISTRIBUTED, REVISED, MODIFIED, TRANSLATED,  ABRIDGED, CONDENSED,
 * EXPANDED,  COLLECTED,  COMPILED,  LINKED,  RECAST, TRANSFORMED OR ADAPTED
 * WITHOUT THE PRIOR WRITTEN CONSENT OF COMERZZIA, S.L. ANY USE OR EXPLOITATION
 * OF THIS WORK WITHOUT AUTHORIZATION COULD SUBJECT THE PERPETRATOR TO
 * CRIMINAL AND CIVIL LIABILITY.
 *
 * CONSULT THE END USER LICENSE AGREEMENT FOR INFORMATION ON ADDITIONAL
 * RESTRICTIONS.
 */

package com.comerzzia.pos.services.promociones.filtro;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.pos.services.promociones.LineaDocumentoPromocionable;
import com.comerzzia.pos.services.promociones.tipos.componente.GrupoComponentePromoBean;
import com.comerzzia.pos.services.promociones.tipos.componente.ItemComponentePromoBean;
import com.comerzzia.pos.services.ticket.lineas.LineaTicket;
import com.comerzzia.pos.services.ticket.promociones.IPromocionLineaTicket;
import com.comerzzia.pos.services.ticket.promociones.PromocionLineaTicket;
import com.comerzzia.pos.services.ticket.promociones.PromocionTicket;
import com.comerzzia.pos.util.bigdecimal.BigDecimalUtil;

@Component
@Scope("prototype")
public class LineasAplicablesPromoBean {

    protected static final Logger log = Logger.getLogger(LineasAplicablesPromoBean.class.getName());

    protected final List<LineaDocumentoPromocionable> lineasAplicables;
    protected BigDecimal cantidadArticulos;
    protected BigDecimal importeLineasConDto;
    protected Integer numCombos;
    protected boolean filtroPromoExclusiva;
    protected boolean filtroNoAcumulableCabecera;
    protected boolean filtroLineasCantidadDecimales;

    public LineasAplicablesPromoBean() {
        lineasAplicables = new ArrayList<>();
        cantidadArticulos = BigDecimal.ZERO;
        importeLineasConDto = BigDecimal.ZERO;
        numCombos = 0;
        filtroPromoExclusiva = false;
        filtroLineasCantidadDecimales = false;
    }

    protected void reset() {
        cantidadArticulos = BigDecimal.ZERO;
        importeLineasConDto = BigDecimal.ZERO;
    }

    public List<LineaDocumentoPromocionable> getLineasAplicables() {
        return lineasAplicables;
    }

    public void setFiltroPromoExclusiva(boolean filtroPromoExclusiva) {
        this.filtroPromoExclusiva = filtroPromoExclusiva;
    }
    
    public void setFiltroLineasCantidadDecimales(boolean filtroLineasCantidadDecimales) {
        this.filtroLineasCantidadDecimales = filtroLineasCantidadDecimales;
    }
    
	public void setLineasAplicables(List<LineaDocumentoPromocionable> lineasAplicables) {
        reset();
        this.lineasAplicables.clear();
        for (LineaDocumentoPromocionable linea : lineasAplicables) {
            if (filtroPromoExclusiva && linea.tienePromocionLineaExclusiva()){
                continue;
            }
            if (!linea.isAdmitePromociones()) {
                continue;
            }
            if (linea.tieneDescuentoManual()){
            	continue;
            }
            if (linea.tieneCambioPrecioManual()){ 
            	continue;
            }
            this.lineasAplicables.add(linea);
            cantidadArticulos = cantidadArticulos.add(linea.getCantidad());
            importeLineasConDto = importeLineasConDto.add(linea.getImporteAplicacionPromocionConDto());
        }
    }

	protected boolean tieneCantidadDecimales(LineaDocumentoPromocionable linea) {
		return !BigDecimalUtil.isIgualACero(linea.getCantidad().remainder(BigDecimal.ONE));
	}
	
    public BigDecimal getCantidadArticulos() {
        return cantidadArticulos;
    }

    public void setCantidadArticulos(BigDecimal cantidadArticulos) {
        this.cantidadArticulos = cantidadArticulos;
    }
    
    /*
     * returns quantitySum-promoted quantity applied
     */    
    public BigDecimal getQuantityRemaining() {
        return cantidadArticulos.subtract(lineasAplicables.stream()
                                    .map(x -> x.getCantidadPromocion())    
                                    .reduce(BigDecimal.ZERO, BigDecimal::add));
    }
    
    /*
     * returns quantitySum-candidate promoted quantity applied
     */
    public BigDecimal getCandidateQuantityRemaining() {
        return cantidadArticulos.subtract(lineasAplicables.stream()
                                    .map(x -> x.getCantidadPromocionCandidata())    
                                    .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    public BigDecimal getImporteLineasConDto() {
        return importeLineasConDto;
    }

    public void setImporteLineasConDto(BigDecimal importeLineasConDto) {
        this.importeLineasConDto = importeLineasConDto;
    }

    public Integer getNumCombos() {
        return numCombos;
    }

    public void setNumCombos(Integer numCombos) {
        this.numCombos = numCombos;
    }


    /**
     * Evalua que se cumplen las condiciones generales sobre el conjunto de líneas aplicables
     *
     * @param condicion
     * @return Boolean
     */
    public boolean isAplicableCondicionesGenerales(GrupoComponentePromoBean condicion) {
        return isAplicableCondicionesGenerales(condicion, cantidadArticulos, importeLineasConDto);
    }

    /** Evalua se la condición general (importe o cantidad) es aplicable sobre la cantidad e importe acumulados
     * de líneas aplicables
     * @param condicion
     * @param cantidadAcumulada
     * @param importeAcumulado
     * @return 
     */
    protected boolean isAplicableCondicionesGenerales(GrupoComponentePromoBean condicion, BigDecimal cantidadAcumulada, BigDecimal importeAcumulado) {
        boolean aplicable = true;
        boolean condicionAplicable;

        if (condicion.isVacio()){
            return true;
        }
        
        // Recorremos todas las reglas de la condición. ESTA CONDICIÓN NO LLEVA GRUPOS
        for (ItemComponentePromoBean itemCondicion : condicion.getReglas()) {

            // Si la condición es un AND y el resultado ya es FALSE no necesitamos continuar evaluando
            if (condicion.isAndNexo() && !aplicable) {
                return false;
            }
            // comprobamos cada regla de la condición con el conjunto de líneas
            condicionAplicable = compruebaCondicion(itemCondicion, cantidadAcumulada, importeAcumulado);
            if (condicion.isAndNexo()) {
                aplicable = aplicable && condicionAplicable;
            }
            else {
                aplicable = aplicable || condicionAplicable;
            }
        }
        return aplicable;
    }
    
    /** Comprueba una regla de condición general (cantidad o importe) comparándo los valores 
     * con la cantidad y filtro indicado por parámetro
     * @param itemCondicion
     * @param cantidadFiltro
     * @param importeFiltro
     * @return 
     */
    protected boolean compruebaCondicion(ItemComponentePromoBean itemCondicion, BigDecimal cantidadAcumulada, BigDecimal importeAcumulado) {
        switch (itemCondicion.getItem()) {
            case ItemComponentePromoBean.ITEM_CANTIDAD:
                return compruebaCantidad(cantidadAcumulada, itemCondicion);
            case ItemComponentePromoBean.ITEM_IMPORTE:
                return compruebaCantidad(importeAcumulado, itemCondicion);
            default:
                log.warn("compruebaCondicion() - Se está intentando comparar una regla con un item no definido: " + itemCondicion.getItem());
                return false;
        }
    }

    protected boolean compruebaCantidad(BigDecimal cantidad, ItemComponentePromoBean itemCondicion) {
        switch (itemCondicion.getOperacion()) {
            case ItemComponentePromoBean.OP_EQUAL:
                return cantidad.compareTo(itemCondicion.getValorAsBigDecimal()) == 0;
            case ItemComponentePromoBean.OP_GREATER:
                return cantidad.compareTo(itemCondicion.getValorAsBigDecimal()) > 0;
            case ItemComponentePromoBean.OP_GREATER_EQ:
                return cantidad.compareTo(itemCondicion.getValorAsBigDecimal()) >= 0;
            case ItemComponentePromoBean.OP_LESS:
                return cantidad.compareTo(itemCondicion.getValorAsBigDecimal()) < 0;               
            case ItemComponentePromoBean.OP_LESS_EQ:
                return cantidad.compareTo(itemCondicion.getValorAsBigDecimal()) <= 0;                  
            default:
                log.warn("compruebaCantidad() - Se está intentando comparar una regla con un operador no definido: " + itemCondicion.getOperacion());
                return false;
        }
    }
    
    public boolean isEmpty(){
        return lineasAplicables.isEmpty();
    }

    /** Aplica el % de descuento indicado de la promoción aplicada del documento a cada una de las líneas aplicables
     * @param promocionTicket
     * @param porcentaje
     */
    public BigDecimal aplicaDescuentoPorcentajeGeneral(PromocionTicket promocionTicket, BigDecimal porcentaje) {
        // Recorremos las líneas aplicables
    	BigDecimal importeAcumuladoDescuento = BigDecimal.ZERO;
        for (LineaDocumentoPromocionable lineaTicket : lineasAplicables) {
            PromocionLineaTicket promocionLinea = new PromocionLineaTicket(promocionTicket);
            BigDecimal importeDescuento = null;
        	BigDecimal importeTotalPromocionesMenosIngreso = ((LineaTicket) lineaTicket).getImporteTotalPromocionesMenosIngreso();
			BigDecimal importeTotalLinea = lineaTicket.getImporteAplicacionPromocionConDto().subtract(importeTotalPromocionesMenosIngreso);
			importeDescuento = BigDecimalUtil.porcentaje(importeTotalLinea, porcentaje);
            importeAcumuladoDescuento = importeAcumuladoDescuento.add(BigDecimalUtil.redondear(importeDescuento));
            promocionLinea.setImporteTotalDto(importeDescuento);
            promocionLinea.setCantidadPromocion(lineaTicket.getCantidad());
            lineaTicket.addPromocion(promocionLinea);
            lineaTicket.recalcularImporteFinal();
        }
        
        // El descuento se aplica sobre el total (promociones de cabecera). Sobre la líneas únicamente se repercute. 
        // Si el prorrateo no se ajusta al descuento total, ajustamos algún decimal en la primera línea
        if (BigDecimalUtil.isMayorACero(importeAcumuladoDescuento) &&  !BigDecimalUtil.isIgual(importeAcumuladoDescuento, BigDecimalUtil.redondear(promocionTicket.getImporteTotalAhorro()))){
        	BigDecimal diferencia = BigDecimalUtil.redondear(promocionTicket.getImporteTotalAhorro()).subtract(importeAcumuladoDescuento);
        	for(LineaDocumentoPromocionable lineaTicket : lineasAplicables){
        		IPromocionLineaTicket promocionLinea =  lineaTicket.getPromocion(promocionTicket.getIdPromocion());
        		if(((LineaTicket) lineaTicket).getImporteTotalConDto().compareTo((BigDecimalUtil.redondear(diferencia))) >= 0){
        			promocionLinea.addImporteTotalDto(diferencia);
            		lineaTicket.recalcularImporteFinal();
            		break;
        		}
        	}
        }
        return importeAcumuladoDescuento;
        
    }

    public BigDecimal aplicaDescuento(PromocionTicket promocionTicket, BigDecimal descuento, BigDecimal precioUnidadRegalada, Boolean dtoPorcentaje, Boolean dtoImporte, BigDecimal cantidadSinDto, BigDecimal cantidadConDto){
        BigDecimal cantidadAplicada = BigDecimal.ZERO;
        BigDecimal cantidadSinAplicar = BigDecimal.ZERO;
        BigDecimal importeAhorroAplicado = BigDecimal.ZERO;
        for (int i = 0; i < lineasAplicables.size();) {
            LineaDocumentoPromocionable linea = lineasAplicables.get(i);

            //Comprobamos si hay cantidad de artículos suficientes
			if (BigDecimalUtil.isMenorOrIgualACero(getCantidadArticulos().subtract(cantidadAplicada))) {
				break;
			}
            
            // Comprobamos si la promoción puede seguir aplicándose en esta línea
            if (BigDecimalUtil.isMayorOrIgual(linea.getCantidadPromocion(), linea.getCantidad())){
                i++; // pasamos a la siguiente línea
                continue; // nueva iteración del bucle
            }

            // Obtenemos la promoción aplicada sobre la línea
            PromocionLineaTicket promocionLinea = linea.getPromocion(promocionTicket.getIdPromocion());
            PromocionLineaTicket promocionCandidata = promocionLinea;
            if (promocionCandidata == null){
                promocionCandidata = new PromocionLineaTicket(promocionTicket);
            }
            
            BigDecimal aumento = BigDecimal.ONE;
            BigDecimal cantidadRestante = cantidadConDto.subtract(cantidadAplicada);
            if (BigDecimalUtil.isMenor(cantidadRestante, BigDecimal.ONE)) {
            	aumento = cantidadRestante;
            }
            if (BigDecimalUtil.isMayor(linea.getCantidadPromocion().add(aumento), linea.getCantidad())) {
            	aumento =  linea.getCantidad().subtract(new BigDecimal(linea.getCantidad().toBigInteger()));
            }
            
            promocionCandidata.addCantidadPromocion(aumento);
            linea.addCantidadPromocion(aumento);

            // Primero contamos todas las líneas que tenemos que dejar sin aplicar descuento (Para NxM)
            if (BigDecimalUtil.isMenor(cantidadSinAplicar, cantidadSinDto)){
                cantidadSinAplicar = cantidadSinAplicar.add(aumento); // aumentamos cantidad de promoción
                if (promocionLinea == null){
                    linea.addPromocion(promocionCandidata);
                }
                continue; // nueva iteración del bucle
            }
            
            // Calculamos el importe de descuento a aplicar
            BigDecimal importeSinDto = linea.getPrecioAplicacionPromocion().multiply(aumento); 
            BigDecimal ahorroAplicable = calcularAhorroAplicable(importeSinDto, dtoPorcentaje, precioUnidadRegalada, dtoImporte, descuento, aumento);
            
           
            // Aplicamos promoción sobre la línea y acumulamos ahorro
            importeAhorroAplicado = importeAhorroAplicado.add(BigDecimalUtil.redondear(ahorroAplicable));
            promocionCandidata.addImporteTotalDto(ahorroAplicable);
            promocionCandidata.addCantidadPromocionAplicada(aumento);
            cantidadAplicada = cantidadAplicada.add(aumento);
            if (promocionLinea == null){
                linea.addPromocion(promocionCandidata);
            }
            linea.recalcularImporteFinal();

            // Comprobamos si ya hemos aplicado el descuento la cantidad de veces indicada
            if (BigDecimalUtil.isIgual(cantidadAplicada, cantidadConDto)){
                return importeAhorroAplicado;
            }
        }
        if (BigDecimalUtil.isMayorACero(cantidadAplicada)){
            return importeAhorroAplicado;
        }
        return null;
    }
    
    public BigDecimal aplicaDescuentoCandidato(PromocionTicket promocionTicket, BigDecimal descuento, BigDecimal precioUnidadRegalada, boolean dtoPorcentaje, boolean dtoImporte, BigDecimal cantidadSinDto, BigDecimal cantidadConDto){
    	BigDecimal cantidadAplicada = BigDecimal.ZERO;
        BigDecimal cantidadSinAplicar = BigDecimal.ZERO;
        BigDecimal importeAhorroAplicado = BigDecimal.ZERO;
        for (int i = 0; i < lineasAplicables.size();) {
            LineaDocumentoPromocionable linea = lineasAplicables.get(i);

            //Comprobamos si hay cantidad de artículos suficientes
			if (BigDecimalUtil.isMenorOrIgualACero(getCantidadArticulos().subtract(cantidadAplicada))) {
				break;
			}
            
            // Comprobamos si la promoción puede seguir aplicándose en esta línea
            if (BigDecimalUtil.isMayorOrIgual(linea.getCantidadPromocionCandidata(), linea.getCantidad())){
                i++; // pasamos a la siguiente línea
                continue; // nueva iteración del bucle
            }

            // Obtenemos la promoción aplicada sobre la línea
            PromocionLineaTicket promocionLinea = linea.getPromocionCandidata(promocionTicket.getIdPromocion());
            PromocionLineaTicket promocionCandidata = promocionLinea;
            if (promocionCandidata == null){
                promocionCandidata = new PromocionLineaTicket(promocionTicket);
            }
            
            BigDecimal aumento = BigDecimal.ONE;
            BigDecimal cantidadRestante = cantidadConDto.subtract(cantidadAplicada);
            if (BigDecimalUtil.isMenor(cantidadRestante, BigDecimal.ONE)) {
            	aumento = cantidadRestante;
            }
            if (BigDecimalUtil.isMayor(linea.getCantidadPromocionCandidata().add(aumento), linea.getCantidad())) {
            	aumento =  linea.getCantidad().subtract(new BigDecimal(linea.getCantidad().toBigInteger()));
            }
            
            promocionCandidata.addCantidadPromocion(aumento);
            linea.addCantidadPromocionCandidata(aumento);

            // Primero contamos todas las líneas que tenemos que dejar sin aplicar descuento (Para NxM)
            if (BigDecimalUtil.isMenor(cantidadSinAplicar, cantidadSinDto)){
                cantidadSinAplicar = cantidadSinAplicar.add(aumento); // aumentamos cantidad de promoción
                if (promocionLinea == null){
                    linea.addPromocionCandidata(promocionCandidata);
                }
                continue; // nueva iteración del bucle
            }
            
            // Calculamos el importe de descuento a aplicar
            BigDecimal importeSinDto = linea.getPrecioAplicacionPromocion();
            BigDecimal ahorroAplicable = calcularAhorroAplicable(importeSinDto, dtoPorcentaje, precioUnidadRegalada, dtoImporte, descuento, aumento);
            
            // Aplicamos promoción sobre la línea y acumulamos ahorro
            importeAhorroAplicado = importeAhorroAplicado.add(BigDecimalUtil.redondear(ahorroAplicable));
            promocionCandidata.addImporteTotalDto(ahorroAplicable);
            promocionCandidata.addCantidadPromocionAplicada(aumento);
            cantidadAplicada = cantidadAplicada.add(aumento);
            if (promocionLinea == null){
                linea.addPromocionCandidata(promocionCandidata);
            }

            // Comprobamos si ya hemos aplicado el descuento la cantidad de veces indicada
            if (BigDecimalUtil.isIgual(cantidadAplicada, cantidadConDto)){
                return importeAhorroAplicado;
            }
        }
        if (BigDecimalUtil.isMayorACero(cantidadAplicada)){
            return importeAhorroAplicado;
        }
        return null;
    }    

	protected BigDecimal calcularAhorroAplicable(BigDecimal importeSinDto, Boolean dtoPorcentaje, BigDecimal precioUnidadRegalada, Boolean dtoImporte, BigDecimal descuento, BigDecimal aumento) {
		BigDecimal ahorroAplicable;

		// El descuento será por porcentaje, calculamos el importe aplicable
		if (dtoPorcentaje != null && dtoPorcentaje) { 
			ahorroAplicable = BigDecimalUtil.porcentaje(importeSinDto, descuento);
		}
		else if (precioUnidadRegalada != null) {
			ahorroAplicable = precioUnidadRegalada.subtract(importeSinDto);
			if (ahorroAplicable.compareTo(BigDecimal.ZERO) < 0) {
				ahorroAplicable = ahorroAplicable.multiply(new BigDecimal(-1));
			}
		}
		else if (dtoImporte != null && dtoImporte) {
			if(BigDecimalUtil.isMenor(importeSinDto,descuento)) {
				ahorroAplicable = BigDecimal.ZERO;			
			} else {
				ahorroAplicable = descuento;
			}				
		}
		else {
			ahorroAplicable = importeSinDto.subtract(descuento.multiply(aumento));
		}
		if (BigDecimalUtil.isMenorACero(ahorroAplicable)) {
			ahorroAplicable = BigDecimal.ZERO;
		}

		return ahorroAplicable;
	}

    public void ordenarLineasPrecioDesc(){
        Comparator<LineaDocumentoPromocionable> comparator = new Comparator<LineaDocumentoPromocionable>() {
            @Override
            public int compare(LineaDocumentoPromocionable o1, LineaDocumentoPromocionable o2) {
                return o2.getPrecioAplicacionPromocion().compareTo(o1.getPrecioAplicacionPromocion());
            }
        };
        Collections.sort(lineasAplicables, comparator);
    }

	public BigDecimal getImporteTotalLineasSinPromociones() {
		BigDecimal totalSinPromociones = BigDecimal.ZERO;
		
		for(LineaDocumentoPromocionable linea : this.getLineasAplicables()) {
			LineaTicket lineaTicket = (LineaTicket) linea;
			totalSinPromociones = totalSinPromociones.add(lineaTicket.getImporteTotalSinDto());
		}
		
		return totalSinPromociones;
	}

	public BigDecimal getImporteTotalLineasConPromociones() {
		BigDecimal totalConPromociones = BigDecimal.ZERO;
		
		for(LineaDocumentoPromocionable linea : this.getLineasAplicables()) {
			LineaTicket lineaTicket = (LineaTicket) linea;
			totalConPromociones = totalConPromociones.add(lineaTicket.getImporteTotalConDto());
		}
		
		return totalConPromociones;
	}
	
	/** Aplica a cada linea una promoción de puntos con los puntos que está generando el artículo sobre el total
     * @param promocionTicket
     */
	public void aplicaPromocionPuntos(PromocionTicket promocionTicket) {
			aplicaPromocionPuntos(promocionTicket, BigDecimal.ONE);
	}

	public void aplicaPromocionPuntos(PromocionTicket promocionTicket,
			BigDecimal puntosEuros) {
		// Recorremos las líneas aplicables
	 	Integer puntosLineasAcumulados = 0;
    	Integer puntosTotales = promocionTicket.getPuntos();
        for (LineaDocumentoPromocionable lineaTicket : lineasAplicables) {
        	PromocionLineaTicket promocionLinea = new PromocionLineaTicket(promocionTicket);
        	Integer puntosLinea = (BigDecimal.valueOf(promocionTicket.getPuntos()).multiply(lineaTicket.getImporteAplicacionPromocionConDto().divide(puntosEuros, 2))).intValue()/puntosTotales;
            promocionLinea.setPuntos(puntosLinea);
            promocionLinea.setCantidadPromocion(lineaTicket.getCantidad());
            lineaTicket.addPromocion(promocionLinea);
            puntosLineasAcumulados = puntosLineasAcumulados+puntosLinea;
        }
        
        // Si el prorrateo no se ajusta al los puntos totales, ajustamos equitativamente todas las lineas
        if ( puntosLineasAcumulados!= puntosTotales){
        	Integer diferencia = promocionTicket.getPuntos()-puntosLineasAcumulados;
        	Integer linesNumber = lineasAplicables.size();
        	for(int i=0;i<diferencia;i++){
        		IPromocionLineaTicket promocionLinea = lineasAplicables.get(i%linesNumber).getPromocion(promocionTicket.getIdPromocion());
        		promocionLinea.setPuntos(promocionLinea.getPuntos()+1);
        	}
        }	
		
	}
}