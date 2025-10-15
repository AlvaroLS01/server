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

package com.comerzzia.pos.services.core.tiendas.cajas;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comerzzia.pos.persistence.core.tiendas.cajas.TiendaCajaBean;
import com.comerzzia.pos.persistence.core.tiendas.cajas.TiendaCajaExample;
import com.comerzzia.pos.persistence.core.tiendas.cajas.TiendaCajaMapper;

@Service
public class TiendaCajaService {
    protected Logger log = Logger.getLogger(TiendaCajaService.class);

	@Autowired
	protected TiendaCajaMapper tiendaCajaMapper;
    
    /** Consulta en BBDD la caja TPV de la tienda asociada al uidCaja y uidActividad indicados.
     * Si no existe, lanza una excepción.
     * @param uidActividad
     * @param uidCaja
     * @return TiendaCajaBean
     * @throws TiendaCajaNotFoundException (Si no existe)
     * @throws TiendaCajaServiceException 
     */
    public TiendaCajaBean consultarTPV(String uidActividad, String uidCaja) throws TiendaCajaNotFoundException, TiendaCajaServiceException{
        try{
            log.debug("consultarTPV() - consultado caja de tienda uidCaja "+ uidCaja);
            TiendaCajaBean tiendaCaja = tiendaCajaMapper.selectByPrimaryKey(uidCaja);

            if(tiendaCaja != null){
                if(tiendaCaja.getUidActividad().equals(uidActividad) && tiendaCaja.getActivo()){
                    return tiendaCaja;    
                }
            }
            throw new TiendaCajaNotFoundException();
        }
        catch(TiendaCajaNotFoundException e){
            String msg = "No existe caja de tienda : " + e.getMessage();
            log.error("consultarTPV() - "+ msg);
            throw new TiendaCajaNotFoundException();
        }
        catch(Exception e){
            String msg = "Se ha producido un error consultando la caja de tienda con uidCaja : " + e.getMessage();
            log.error("consultarTPV() - "+ msg);
            throw new TiendaCajaServiceException(e);
        }
    }
    
    public TiendaCajaBean consultarCaja(String uidActividad, String storeCode, String tillId) throws TiendaCajaNotFoundException, TiendaCajaServiceException{
        try{
            log.debug("consultarCaja() - consultado caja de tienda "+ storeCode + " caja " + tillId);
            TiendaCajaExample example = new TiendaCajaExample();
            example.or().andUidActividadEqualTo(uidActividad).andCodAlmacenEqualTo(storeCode).andCodcajaEqualTo(tillId);
			List<TiendaCajaBean> cajas = tiendaCajaMapper.selectByExample(example);

            if(cajas != null && cajas.size() == 1){
            	TiendaCajaBean tiendaCaja = cajas.get(0);
            	
                if(tiendaCaja.getActivo()){
                    return tiendaCaja;    
                }
            }
            throw new TiendaCajaNotFoundException();
        }
        catch(TiendaCajaNotFoundException e){
            String msg = "No existe caja de tienda : " + e.getMessage();
            log.error("consultarCaja() - "+ msg);
            throw new TiendaCajaNotFoundException();
        }
        catch(Exception e){
            String msg = "Se ha producido un error consultando la caja de tienda con uidCaja : " + e.getMessage();
            log.error("consultarCaja() - "+ msg);
            throw new TiendaCajaServiceException(e);
        }
    }
    
    public void grabarConfiguracionDispositivos(TiendaCajaBean tienda) throws TiendaCajaServiceException{
        try {
        	log.debug("grabarConfiguracionDispositivos() - Guardando la configuración del dispositivo");
            log.trace(new String(tienda.getConfiguracion(),"UTF-8"));
            
            tiendaCajaMapper.updateByPrimaryKeyWithBLOBs(tienda);
        }
        catch (UnsupportedEncodingException ex) {
            log.error("grabarConfiguracionDispositivos() - Error en la codificacion del xml.");
            throw new TiendaCajaServiceException(ex);
        }catch(Exception e){
            String msg = "Se ha producido un error consultando la caja de tienda con uidCaja : " + e.getMessage();
            log.error("grabarConfiguracionDispositivos() - "+ msg, e);
            throw new TiendaCajaServiceException(e);
        }
    }
    
    public void actualizarUidPOS(TiendaCajaBean tiendaCajaActual, String newUidPos) throws TiendaCajaServiceException{
         try {
         	log.debug("actualizarUidPOS() - Actualizando UID_TPV");

             TiendaCajaBean tiendaCaja = new TiendaCajaBean();
             tiendaCaja.setUidTpv(newUidPos);
             TiendaCajaExample example = new TiendaCajaExample();
             example.or()
             	.andUidActividadEqualTo(tiendaCajaActual.getUidActividad())
             	.andCodcajaEqualTo(tiendaCajaActual.getCodcaja())
             	.andCodAlmacenEqualTo(tiendaCajaActual.getCodAlmacen());
             
             tiendaCajaMapper.updateByExampleSelective(tiendaCaja, example);
         }catch(Exception e){
             String msg = "Se ha producido un error actualizando la caja de tienda con uidCaja : " + e.getMessage();
             log.error("grabarConfiguracionDispositivos() - "+ msg, e);
             throw new TiendaCajaServiceException(e);
         }
    }
    
}
