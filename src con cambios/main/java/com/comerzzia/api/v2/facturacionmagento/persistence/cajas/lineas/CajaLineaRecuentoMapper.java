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
package com.comerzzia.api.v2.facturacionmagento.persistence.cajas.lineas;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CajaLineaRecuentoMapper {
    int countByExample(CajaLineaRecuentoExample example);

    int deleteByExample(CajaLineaRecuentoExample example);

    int deleteByPrimaryKey(CajaLineaRecuentoKey key);

    int insert(CajaLineaRecuentoBean record);

    int insertSelective(CajaLineaRecuentoBean record);

    List<CajaLineaRecuentoBean> selectByExampleWithRowbounds(CajaLineaRecuentoExample example, RowBounds rowBounds);

    List<CajaLineaRecuentoBean> selectByExample(CajaLineaRecuentoExample example);

    CajaLineaRecuentoBean selectByPrimaryKey(CajaLineaRecuentoKey key);

    Integer selectMaximaLineaRecuento(CajaLineaRecuentoExample example);
    
    int updateByExampleSelective(@Param("record") CajaLineaRecuentoBean record, @Param("example") CajaLineaRecuentoExample example);

    int updateByExample(@Param("record") CajaLineaRecuentoBean record, @Param("example") CajaLineaRecuentoExample example);

    int updateByPrimaryKeySelective(CajaLineaRecuentoBean record);

    int updateByPrimaryKey(CajaLineaRecuentoBean record);
}