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
package com.comerzzia.pos.persistence.core.tiendas.cajas;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TiendaCajaMapper {
    int countByExample(TiendaCajaExample example);

    int deleteByExample(TiendaCajaExample example);

    int deleteByPrimaryKey(String uidCaja);

    int insert(TiendaCajaBean record);

    int insertSelective(TiendaCajaBean record);

    List<TiendaCajaBean> selectByExampleWithBLOBsWithRowbounds(TiendaCajaExample example, RowBounds rowBounds);

    List<TiendaCajaBean> selectByExampleWithBLOBs(TiendaCajaExample example);

    List<TiendaCajaBean> selectByExampleWithRowbounds(TiendaCajaExample example, RowBounds rowBounds);

    List<TiendaCajaBean> selectByExample(TiendaCajaExample example);

    TiendaCajaBean selectByPrimaryKey(String uidCaja);

    int updateByExampleSelective(@Param("record") TiendaCajaBean record, @Param("example") TiendaCajaExample example);

    int updateByExampleWithBLOBs(@Param("record") TiendaCajaBean record, @Param("example") TiendaCajaExample example);

    int updateByExample(@Param("record") TiendaCajaBean record, @Param("example") TiendaCajaExample example);

    int updateByPrimaryKeySelective(TiendaCajaBean record);

    int updateByPrimaryKeyWithBLOBs(TiendaCajaBean record);

    int updateByPrimaryKey(TiendaCajaBean record);
}