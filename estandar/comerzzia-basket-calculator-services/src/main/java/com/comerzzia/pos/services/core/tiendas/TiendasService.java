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

package com.comerzzia.pos.services.core.tiendas;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comerzzia.pos.persistence.almacenes.AlmacenBean;
import com.comerzzia.pos.persistence.almacenes.AlmacenKey;
import com.comerzzia.pos.persistence.almacenes.AlmacenMapper;
import com.comerzzia.pos.persistence.core.tiendas.TiendaBean;
import com.comerzzia.pos.persistence.core.tiendas.TiendaKey;
import com.comerzzia.pos.persistence.core.tiendas.TiendaMapper;

@Service
public class TiendasService {
    protected final Logger log = Logger.getLogger(TiendasService.class);
        
    @Autowired
    protected TiendaMapper tiendaMapper;
    
    @Autowired
    protected AlmacenMapper almacenMapper;

    /** Devuelve la tienda con el código almacén indicado que se encuentre en
     * base de datos. La tienda contiene tanto los datos de tienda como de
     * almacén. Debe de estar activa.
     * @param uidActividad
     * @param codAlmacen
     * @return :: Tienda
     * @throws TiendaNotFoundException :: Lanzada si no existe la tienda con el
     * código indicado
     * @throws TiendasServiceException
     */
    public Tienda consultarTienda(String uidActividad, String codAlmacen) throws TiendaNotFoundException, TiendasServiceException {
        try {
            TiendaBean tiendaBean;
            TiendaKey keyTienda = new TiendaKey();
            keyTienda.setCodAlmacen(codAlmacen);
            keyTienda.setUidActividad(uidActividad);
            log.debug("consultarTienda() - consultado tienda con codAlmacen " + codAlmacen);
            tiendaBean = tiendaMapper.selectByPrimaryKey(keyTienda);
            if (tiendaBean != null) {
                if (tiendaBean.getActivo()) {
                    AlmacenBean almacenBean;
                    AlmacenKey keyAlmacen = new AlmacenKey();
                    keyAlmacen.setCodAlmacen(codAlmacen);
                    keyAlmacen.setUidActividad(uidActividad);
                    log.debug("consultarTienda() - consultando almacen con codAlmacen " + codAlmacen);
                    almacenBean = almacenMapper.selectByPrimaryKey(keyAlmacen);
                    if (almacenBean != null) {
                        Tienda tienda = new Tienda();
                        tienda.setAlmacenBean(almacenBean);
                        tienda.setTiendaBean(tiendaBean);
                        return tienda;
                    }
                }
            }
            throw new TiendaNotFoundException();
        }
        catch (TiendaNotFoundException e) {
            log.error("consultarTienda() - No existe tienda para el codigo de tienda " + codAlmacen);
            throw e;
        }
        catch (Exception e) {
            String msg = "Se ha producido un error consultando la entidad con codigo " + codAlmacen + ": " + e.getMessage();
            log.error("consultarTienda() - " + msg, e);
            throw new TiendasServiceException(e);
        }
    }
}
