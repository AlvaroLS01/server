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
package com.comerzzia.core.basketcalculator.util.paginacion;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.comerzzia.core.basketcalculator.util.base.ParametrosBuscarBean;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "paginacion")
public class PaginaResultados {
	
	@XmlTransient
	protected List<?> pagina = null;
	@XmlElement(name="tamanyoPagina")
	private int tamañoPagina = 1;
	private int numPagina = 0;
	private int totalResultados = 0;
    
    
    public PaginaResultados() {
    }
    
    
    public PaginaResultados(ParametrosBuscarBean param, List<?> pagina) {
    	this.setTamañoPagina(param.getTamañoPagina());
    	this.setNumPagina(param.getNumPagina());
    	this.setPagina(pagina);
    }


    public void setPagina(List<?> pagina) {
        this.pagina = pagina;
    }


    @JsonIgnore
    public List<?> getPagina() {
        return pagina;
    }


    public void setTamañoPagina(int tamañoPagina) {
        this.tamañoPagina = tamañoPagina;
    }


    @JsonProperty(value="tamanyoPagina")
    public int getTamañoPagina() {
        return tamañoPagina;
    }


    public void setNumPagina(int numPagina) {
        this.numPagina = numPagina;
    }
    
    
    public void setNumPagina(String numPagina) {
        this.numPagina = Integer.parseInt(numPagina);
    }


    public int getNumPagina() {
        return numPagina;
    }


    public void setTotalResultados(int totalResultados) {
        this.totalResultados = totalResultados;
    }


    public int getTotalResultados() {
        return totalResultados;
    }
    
    @XmlElement(name = "numeroTotalPaginas")
    public int getNumeroTotalPaginas() {
        if (totalResultados <=0 || tamañoPagina <= 0) {
           return 0;
        }
        
        return (int) Math.ceil((double)totalResultados/(double)tamañoPagina);
    }
    
    @XmlElement(name = "esUltimaPagina")
    public boolean isEsUltimaPagina() {
        return (getNumPagina() >= getNumeroTotalPaginas());
    }
    
    @XmlElement(name = "esPrimeraPagina")
    public boolean isEsPrimeraPagina() {
        return (getNumPagina() == 1);
    }
    
    @XmlElement(name = "siguientePagina")
    public int getSiguientePagina() {
        int pag = getNumPagina();
        
        if (pag >= getNumeroTotalPaginas()) {
            return getNumeroTotalPaginas();
        }
        else {
            return pag+1;
        }
    }
    
    @XmlElement(name = "anteriorPagina")
    public int getAnteriorPagina() {
        int pag = getNumPagina();
        
        if (pag <= 1) {
            return 1;
        }
        else {
            return pag-1;
        }
    }
    
    @XmlElement(name = "inicio")
    public int getInicio() {
        return ((getNumPagina()-1) * getTamañoPagina()) + 1;
    }
}
