package com.comerzzia.pos.services.promociones.tipos.especificos;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.core.basketcalculator.util.xml.XMLDocument;
import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentException;
import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentNode;
import com.comerzzia.pos.services.promociones.DocumentoPromocionable;
import com.comerzzia.pos.services.promociones.filtro.FiltroLineasPromocion;
import com.comerzzia.pos.services.promociones.filtro.LineasAplicablesPromoBean;
import com.comerzzia.pos.services.promociones.tipos.PromocionCabecera;
import com.comerzzia.pos.services.promociones.tipos.componente.CondicionPrincipalPromoBean;
import com.comerzzia.pos.services.ticket.cupones.CuponAplicadoTicket;
import com.comerzzia.pos.services.ticket.promociones.IPromocionTicket;

@Component
@Scope("prototype")
public class PromocionTextoBean extends PromocionCabecera{
	
	protected CondicionPrincipalPromoBean condiciones;
	protected String imagenPromocion;
	protected boolean visibleVentas;

	@Override
	public void leerDatosPromocion(byte[] datosPromocion) {
		try {
            XMLDocument xmlPromocion = new XMLDocument(datosPromocion);
            condiciones = new CondicionPrincipalPromoBean(xmlPromocion.getNodo("condicionLineas"));
            
            String storeLanguageCode = getStoreLanguageCode();
            
            String textPromo = null;
            String textPromoDefault = null;
        	List<XMLDocumentNode> textPromoNodes = xmlPromocion.getNodos("textoPromocion");
        	for(XMLDocumentNode textPromoNode : textPromoNodes) {
        		String textPromoLanguageCode = textPromoNode.getAtributoValue("lang", true);
        		if(StringUtils.isNotBlank(textPromoLanguageCode)){
        			if(textPromoLanguageCode.equals(storeLanguageCode)) {
        				textPromo = textPromoNode.getValue();
        				break;
        			}
        		}else {
        			textPromoDefault = textPromoNode.getValue();
        		}
        	}

        	if(StringUtils.isBlank(textPromo)) {
        		textPromo = textPromoDefault;
        	}

            setTextoPromocion(textPromo);
            XMLDocumentNode nodoImagenPromocion = xmlPromocion.getNodo("imagenPromocion", true);
            if(nodoImagenPromocion != null) {
            	 setImagenPromocion(nodoImagenPromocion.getValue());
            }
            
            XMLDocumentNode nodoVisibleVentas = xmlPromocion.getNodo("visibleVentas", true);
            if(nodoVisibleVentas != null) {
            	setVisibleVentas(nodoVisibleVentas.getValueAsBoolean());
            }
           
        }
        catch (XMLDocumentException e) {
            log.error("Error al leer los datos de la promoción de tipo texto: " + e.getMessage(), e);
        }
	}

	@Override
	public boolean aplicarPromocion(DocumentoPromocionable<IPromocionTicket> documento) {
		log.trace("aplicarPromocion() - " + this);
        // Obtenemos las líneas aplicables según el filtro configurado
        FiltroLineasPromocion filtroLineas = createFiltroLineasPromocion(documento);
        filtroLineas.setFiltrarPromoExclusivas(false); // Da igual que las líneas tengan una promoción exclusiva
        LineasAplicablesPromoBean lineasAplicables = filtroLineas.getNumCombosCondicion(condiciones);
        if (lineasAplicables.isEmpty() && !condiciones.isVacio()) {
            log.trace(this + " aplicarPromocion() - La promoción no se puede aplicar por no existir líneas aplicables en el documento .");
            return false;
        }
        
        // Aplicamos la promoción sobre el ticket
        IPromocionTicket promocionTicket = documento.getPromocion(getIdPromocion());
        if(promocionTicket == null) {
        	promocionTicket = createPromocionTicket(customerCoupon);
        	documento.addPromocion(promocionTicket);
        }
        
        if(customerCoupon != null) {
	        CuponAplicadoTicket cupon = documento.getCuponAplicado(customerCoupon.getCouponCode());
	        if (cupon != null){
	        	cupon.setTextoPromocion(promocionTicket.getTextoPromocion());
	        }
        }

        return true;
	}

	public void setImagenPromocion(String imagenPromocion) {
		this.imagenPromocion = imagenPromocion;
	}
	
	public void setVisibleVentas(boolean visibleVentas) {
		this.visibleVentas = visibleVentas;
	}

	// La promoción será final si no se tienen que mostrar los mensajes al cajero
	
	@Override
	public boolean isAplicacionFinal() {
		return !visibleVentas;
	}
	
	@Override
	public boolean isAplicacionCabecera() {
		return visibleVentas;
	}
	
}
