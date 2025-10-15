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


package com.comerzzia.pos.services.promociones.tipos.componente;

import java.math.BigDecimal;

import com.comerzzia.core.basketcalculator.util.numeros.Numero;


public class ItemComponentePromoBean implements Comparable<ItemComponentePromoBean>, IComponentePromoBean{

	public static final String ITEM_COLECTIVO_FIDELIZADOS 	= "COLECTIVOS";
	public static final String ITEM_CATEGORIZACIONES   		= "CATEGORIZACIONES";
	
    public static final String ITEM_ARTICULOS      = "ARTICULOS";
    public static final String ITEM_PROVEEDOR      = "PROVEEDORES";
    public static final String ITEM_SECCION        = "SECCION";
    public static final String ITEM_FAMILIAS       = "FAMILIAS";
    public static final String ITEM_MARCA     	   = "MARCA";
    public static final String ITEM_PRECIO     	   = "PRECIO";
    public static final String ITEM_IMPORTE_MULT   = "IMPORTE-MULTIPLE";  //Por compatibilidad versión anterior, éste es MULTIPLE y cantidad es UNICA
    public static final String ITEM_CANTIDAD_MULT  = "CANTIDAD";
    public static final String ITEM_IMPORTE        = "IMPORTE";
    public static final String ITEM_CANTIDAD       = "CANTIDAD-UNICA";    //Por compatibilidad versión anterior, éste es UNICA e importe es MULTIPLE
    public static final String ITEM_DIA_SEMANA     = "DIA";
    public static final String ITEM_HORA           = "HORA";
    public static final String ITEM_ETIQUETAS      = "ETIQUETAS";
    public static final String ITEM_ETIQUETAS_FIDELIZADOS = "ETIQUETAS_FIDELIZADOS";
    public static final String ITEM_IMPORTE_TOTAL = "IMPORTE-TOTAL";
                                                   
    public static final String OP_EQUAL            = "equal";
    public static final String OP_NO_EQUAL         = "not_equal";
    public static final String OP_GREATER          = "greater";
    public static final String OP_GREATER_EQ       = "greater_or_equal";
    public static final String OP_LESS             = "less";
    public static final String OP_LESS_EQ   	   = "less_or_equal";
    public static final String OP_BETWEEN          = "between";
    public static final String OP_CONTAINS         = "contains";
    public static final String OP_NOT_CONTAINS     = "not_contains";
    public static final String OP_BEGINS_WITH      = "begins_with";
    public static final String OP_NOT_BEGINS_WITH  = "not_begins_with";
    public static final String OP_ENDS_WITH        = "ends_with";
    public static final String OP_NOT_ENDS_WITH    = "not_ends_with";

    public static final String OP_MAYOR           = "mayor";
    public static final String OP_MAYOR_EQ        = "mayor_o_igual";
    
    protected String item;
    protected String operacion;
    protected String valor;
    protected String valorAdicional;
    protected String valorCantidad;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
    public String getValorAsString() {
        return valor;
    }
    
    public Integer getValorAsInteger() {
        return Numero.getEntero(valor, null);
    }
    
    public Double getValorAsDouble() {
        return Numero.desformatea(valor, null);
    }

    public BigDecimal getValorAsBigDecimal() {
        return new BigDecimal(valor);
    }
    
    public Long getValorAsLong() {
        return Numero.desformateaLong(valor, null);
    }

    
    public String getValorAdicionalAsString() {
        return valorAdicional;
    }
    
    public Integer getValorAdicionalAsInteger() {
        return Numero.getEntero(valorAdicional, null);
    }
    
    public Double getValorAdicionalAsDouble() {
        return Numero.desformatea(valorAdicional, null);
    }

    public BigDecimal getValorAdicionalAsBigDecimal() {
        return new BigDecimal(valorAdicional);
    }
    
    public Long getValorAdicionalAsLong() {
        return Numero.desformateaLong(valorAdicional, null);
    }
    
    
    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public void setValorAdicional(String valorAdicional) {
        this.valorAdicional = valorAdicional;
    }
    
    public void setValorCantidad(String valorCantidad) {
        this.valorCantidad = valorCantidad;
    }
    
    public String getValorCantidadAsString() {
        return valorCantidad;
    }
    
    public Integer getValorCantidadAsInteger() {
        return Numero.getEntero(valorCantidad, null);
    }
    
    public Double getValorCantidadAsDouble() {
        return Numero.desformatea(valorCantidad, null);
    }

    public BigDecimal getValorCantidadAsBigDecimal() {
        return new BigDecimal(valorCantidad);
    }
    
    public Long getValorCantidadAsLong() {
        return Numero.desformateaLong(valorCantidad, null);
    }
    public boolean isOperacionEqual(){
        return operacion.equals(OP_EQUAL);
    }
    public boolean isOperacionNoEqual(){
        return operacion.equals(OP_NO_EQUAL);
    }
    
    public boolean isOperacionBeginsWith(){
    	return operacion.equals(OP_BEGINS_WITH);
    }
    public boolean isOperacionNotBeginWith(){
    	return operacion.equals(OP_NOT_BEGINS_WITH);
    }

    @Override
    public int compareTo(ItemComponentePromoBean o) {
        try{
            return o.getValorAsBigDecimal().compareTo(getValorAsBigDecimal());
        }
        catch(Exception e){ // Si tenemos algún error de conversión a BigDecimal, consideramos los valores iguales
            return 0;
        }
    }

    @Override
    public boolean isTipoItemRegla() {
        return true;
    }

    @Override
    public boolean isTipoGrupoReglas() {
        return false;
    }

    
    public boolean isReglaCantidad(){
    	return getItem().equals(ITEM_CANTIDAD);
    }
    
    public boolean isReglaImporte(){
    	return getItem().equals(ITEM_IMPORTE);
    }
    
    public boolean isReglaCantidadMultiple(){
        return getItem().equals(ITEM_CANTIDAD_MULT);
    }
    
    public boolean isReglaImporteMultiple(){
        return getItem().equals(ITEM_IMPORTE_MULT);
    }
    
    public boolean isReglaMultiple(){
        return isReglaCantidadMultiple() || isReglaImporteMultiple();
    }
    
}
