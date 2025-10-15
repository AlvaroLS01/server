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

import java.util.ArrayList;
import java.util.List;

import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentNode;
import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentNodeNotFoundException;


public class GrupoComponentePromoBean implements IComponentePromoBean{

    boolean andNexo = false;
    boolean orNexo = false;
    boolean vacio = false;

    List<ItemComponentePromoBean> reglas;
    List<GrupoComponentePromoBean> grupos;
    List<IComponentePromoBean> componentes;

    public GrupoComponentePromoBean() {
        reglas = new ArrayList<>();
        grupos = new ArrayList<>();
        componentes = new ArrayList<>();
        vacio = true;
    }

    public GrupoComponentePromoBean(XMLDocumentNode nodo) throws XMLDocumentNodeNotFoundException {
        reglas = new ArrayList<>();
        grupos = new ArrayList<>();
        componentes = new ArrayList<>();
        vacio = true;

        XMLDocumentNode condition = nodo.getNodo("condition",true);
        if (condition == null){
            // No se ha indicado reglas ni grupos. La condición es vacía
            return;
        }
        vacio = false;
        String nexo = condition.getValue();
        if (nexo.equals("AND")) {
            andNexo = true;
        }
        if (nexo.equals("OR")) {
            orNexo = true;
        }
        
        XMLDocumentNode rules = nodo.getNodo("rules");
        for (XMLDocumentNode rule : rules.getHijos()) {
            try {
                String grupoHijo = rule.getNodo("rules").getValue();
                if (grupoHijo != null && !grupoHijo.isEmpty()) {
                    GrupoComponentePromoBean regla = new GrupoComponentePromoBean(rule);
					grupos.add(regla);
                    componentes.add(regla);
                }
            }
            catch (XMLDocumentNodeNotFoundException e) {
                //No hemos encontrado el nodo rules, es una regla simple
                ItemComponentePromoBean item = new ItemComponentePromoBean();
                item.setItem(rule.getNodo("id").getValue());
                item.setOperacion(rule.getNodo("operator").getValue());
                item.setValor(rule.getNodo("value").getValue());
                XMLDocumentNode valorAdicional = rule.getNodo("des", true);
                if (valorAdicional != null){
                    item.setValorAdicional(valorAdicional.getValue());
                }
                XMLDocumentNode valorCantidad = rule.getNodo("quantity", true);
                if (valorCantidad != null){
                    item.setValorCantidad(valorCantidad.getValue());
                }
                reglas.add(item);
                componentes.add(item);
            }
        }
    }

    public boolean isAndNexo() {
        return andNexo;
    }

    public void setAndNexo(boolean andNexo) {
        this.andNexo = andNexo;
    }

    public void setAndNexo(String andNexo) {
        if (andNexo.equals("true")) {
            andNexo = "S";
        }
        else if (andNexo.equals("false")) {
            andNexo = "N";
        }
        this.andNexo = andNexo.equals("S");
    }

    public String getAndNexo() {
        return (andNexo) ? "S" : "N";
    }

    public boolean isOrNexo() {
        return orNexo;
    }

    public void setOrNexo(boolean orNexo) {
        this.orNexo = orNexo;
    }

    public void setOrNexo(String orNexo) {
        if (orNexo.equals("true")) {
            orNexo = "S";
        }
        else if (orNexo.equals("false")) {
            orNexo = "N";
        }
        this.orNexo = orNexo.equals("S");
    }

    public String getOrNexo() {
        return (orNexo) ? "S" : "N";
    }

    public List<ItemComponentePromoBean> getReglas() {
        return reglas;
    }

    public void setReglas(List<ItemComponentePromoBean> reglas) {
        this.reglas = reglas;
    }

    public List<GrupoComponentePromoBean> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<GrupoComponentePromoBean> grupos) {
        this.grupos = grupos;
    }

    public boolean isVacio() {
        return vacio;
    }
    
    @Override
    public boolean isTipoItemRegla() {
        return false;
    }

    @Override
    public boolean isTipoGrupoReglas() {
        return true;
    }

	public List<IComponentePromoBean> getComponentes() {
		return componentes;
	}

	public void setComponentes(List<IComponentePromoBean> componentes) {
		this.componentes = componentes;
	}

}
