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
package com.comerzzia.pos.services.codBarrasEsp;

import java.text.DecimalFormatSymbols;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comerzzia.pos.persistence.codBarrasEspeciales.CodigoBarrasEspecialBean;
import com.comerzzia.pos.persistence.codBarrasEspeciales.CodigoBarrasEspecialExample;
import com.comerzzia.pos.persistence.codBarrasEspeciales.CodigoBarrasEspecialMapper;
import com.comerzzia.pos.services.core.sesion.Sesion;

@Service
public class CodBarrasEspecialesService {	
	protected final Logger log = Logger.getLogger(CodBarrasEspecialesService.class);
		
	@Autowired
	Sesion sesion;
	
	@Autowired
	protected CodigoBarrasEspecialMapper codigoBarrasMapper;
	
	public List<CodigoBarrasEspecialBean> obtenerCodigosBarras(String uidActividad) {
		log.trace("obtenerCodigosBarras() -");
        CodigoBarrasEspecialExample example = new CodigoBarrasEspecialExample();
        CodigoBarrasEspecialExample.Criteria criteria = example.createCriteria();
        criteria.andUidActividadEqualTo(uidActividad);
        
        return codigoBarrasMapper.selectByExample(example);
	}
	
	
	public CodigoBarrasEspecialBean esCodigoBarrasEspecial(String codigo){
    	CodigoBarrasEspecialBean codBarrasEspecial = null;
    	
		for(CodigoBarrasEspecialBean codigoBarras: sesion.getAplicacion().getSpecialBarcodes()){
			if (codigo.length() < codigoBarras.getPrefijo().length()) {
				continue;
			}
			String prefijoCodigo = codigo.substring(0, codigoBarras.getPrefijo().length());

			if(prefijoCodigo.equals(codigoBarras.getPrefijo())){
				codBarrasEspecial = codigoBarras;
				break;
			}
		}
		
        if(codBarrasEspecial != null){
        	CodigoBarrasEspecialBean codigoBarras = new CodigoBarrasEspecialBean();
        	codigoBarras.setCodigoIntroducido(codigo);
        	codigoBarras.setDescripcion(codBarrasEspecial.getDescripcion());
        	codigoBarras.setPrefijo(codBarrasEspecial.getPrefijo());
        	
    		String[] codArtCodBar = codBarrasEspecial.getCodart().split("\\|");
    		String[] ticketCodBar = codBarrasEspecial.getCodticket().split("\\|");
    		String[] cantidadCodBar = codBarrasEspecial.getCantidad().split("\\|");
    		String[] precioCodBar = codBarrasEspecial.getPrecio().split("\\|");

    		if (codArtCodBar.length > 0) {
				int inicioCodArt = Integer.valueOf(codArtCodBar[0]) - 1;
				int cantCodArt = Integer.valueOf(codArtCodBar[1]);
				if(cantCodArt > 0){
					String codArt = codigo.substring(inicioCodArt, inicioCodArt + cantCodArt);
					codigoBarras.setCodart(codArt);
				}
    		}
    		if (ticketCodBar.length > 0) {
	    		int inicioCodTicket = Integer.valueOf(ticketCodBar[0]) - 1;
	    		int cantCodTicket = Integer.valueOf(ticketCodBar[1]);
	    		if(cantCodTicket > 0){
	    			String codTicket = codigo.substring(inicioCodTicket, inicioCodTicket+cantCodTicket);
	    			codigoBarras.setCodticket(codTicket);
	    		}
    		}
    		
    		char decimalSeparator = DecimalFormatSymbols.getInstance().getDecimalSeparator();
    		int posCursor = 0;
    		
    		int inicioPrecio = Integer.valueOf(precioCodBar[0]) - 1;
    		int cantPrecioEntero = Integer.valueOf(precioCodBar[1]);
    		int cantPrecioDecimal = Integer.valueOf(precioCodBar[2]);
    		if(cantPrecioEntero > 0 || cantPrecioDecimal > 0) {
	    		posCursor =  inicioPrecio;
	    		String precio = "";
	    		if(cantPrecioEntero > 0) {
	    			precio = codigo.substring(posCursor, posCursor+cantPrecioEntero);
	    		} else {
	    			precio = "0";
	    		}
	    		precio = precio.concat(decimalSeparator+"");
	    		posCursor += cantPrecioEntero;
				if (cantPrecioDecimal > 0) {
	    			precio = precio.concat(codigo.substring(posCursor, posCursor+cantPrecioDecimal));
	    		} else {
	    			precio = precio.concat("0"); 
	    		}
	    		codigoBarras.setPrecio(precio);
    		}
    		
    		int inicioCantidad = Integer.valueOf(cantidadCodBar[0]) - 1;
    		int cantCantidadEntero = Integer.valueOf(cantidadCodBar[1]);
    		int cantCantidadDecimal = Integer.valueOf(cantidadCodBar[2]);
    		if(cantCantidadEntero > 0 || cantCantidadDecimal > 0) {
	    		posCursor = inicioCantidad;
	    		String cantCodBar = null;
	    		if (cantCantidadEntero > 0) {
	    			cantCodBar = codigo.substring(posCursor, posCursor+cantCantidadEntero);
	    		} else {
	    			cantCodBar = "0";
	    		}
	    		posCursor += cantCantidadEntero;
	    		cantCodBar = cantCodBar.concat(decimalSeparator+"");
	    		if (cantCantidadDecimal > 0) {
	    			cantCodBar = cantCodBar.concat(codigo.substring(posCursor, posCursor+cantCantidadDecimal));
	    		} else {
	    			cantCodBar = cantCodBar.concat("0");
	    		}
	    		codigoBarras.setCantidad(cantCodBar);
    		}
    		
    		return codigoBarras;
        }

    	return null;
    }

}
