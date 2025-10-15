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

import java.util.ArrayList;
import java.util.List;


public class ListaPaginada {

	private List<?> lista;
    private int tamañoPagina = 1;
    private int pagina = 1;
    
    public ListaPaginada() {
    }


    public void setLista(List<?> lista) {
        this.lista = lista;
    }


    public List<?> getLista() {
        return lista;
    }


    public void setTamañoPagina(int tamañoPagina) {
        this.tamañoPagina = tamañoPagina;
    }


    public int getTamañoPagina() {
        return tamañoPagina;
    }
    
    
    public boolean isEsUltimaPagina() {
        return (getPagina() >= getNumeroTotalPaginas());
    }
    
    
    public boolean isEsPrimeraPagina() {
        return (getPagina() == 1);
    }
    
    
    public int getNumeroTotalPaginas() {
        if (lista == null || tamañoPagina <= 0) {
           return 0;
        }
        
        return (int) Math.ceil((double)lista.size()/(double)tamañoPagina);
    }
    
    
    public void setPagina(int pagina) {
        this.pagina = pagina;
    }


    public int getPagina() {
        int pag = pagina;
        int numeroTotalPaginas = getNumeroTotalPaginas();
        
        if (pag > numeroTotalPaginas) {
            pag = numeroTotalPaginas;
        }
        
        if (pag < 1) {
            pag = 1;
        }
        
        return pag;
    }
    
    
    public List<Object> getElementosPagina() {
        if (lista == null || tamañoPagina <= 0) {
            return null;
        }
        
        List<Object> lstPagina = new ArrayList<Object>(tamañoPagina);
        
        int inicio = (getPagina()-1) * tamañoPagina;  // Primer elemento a devolver
        int añadidos = 0;  // Numero de elementos añadidos
        for (int i=inicio; i < lista.size() && añadidos < tamañoPagina; i++) {
            lstPagina.add(lista.get(i));
            añadidos++;
        }
        
        return lstPagina;
    }
    
    
    public int getSiguientePagina() {
        int pag = getPagina();
        
        if (pag >= getNumeroTotalPaginas()) {
            return getNumeroTotalPaginas();
        }
        else {
            return pag+1;
        }
    }
    
    
    public int getAnteriorPagina() {
        int pag = getPagina();
        
        if (pag <= 1) {
            return 1;
        }
        else {
            return pag-1;
        }
    }
}
