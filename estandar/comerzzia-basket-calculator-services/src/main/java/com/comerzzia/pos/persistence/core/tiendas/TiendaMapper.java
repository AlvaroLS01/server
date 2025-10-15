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
package com.comerzzia.pos.persistence.core.tiendas;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TiendaMapper {
    int countByExample(TiendaExample example);

    int deleteByExample(TiendaExample example);

    int deleteByPrimaryKey(TiendaKey key);

    int insert(TiendaBean record);

    int insertSelective(TiendaBean record);

    List<TiendaBean> selectByExampleWithBLOBsWithRowbounds(TiendaExample example, RowBounds rowBounds);

    List<TiendaBean> selectByExampleWithBLOBs(TiendaExample example);

    List<TiendaBean> selectByExampleWithRowbounds(TiendaExample example, RowBounds rowBounds);

    List<TiendaBean> selectByExample(TiendaExample example);

    TiendaBean selectByPrimaryKey(TiendaKey key);

    int updateByExampleSelective(@Param("record") TiendaBean record, @Param("example") TiendaExample example);

    int updateByExampleWithBLOBs(@Param("record") TiendaBean record, @Param("example") TiendaExample example);

    int updateByExample(@Param("record") TiendaBean record, @Param("example") TiendaExample example);

    int updateByPrimaryKeySelective(TiendaBean record);

    int updateByPrimaryKeyWithBLOBs(TiendaBean record);

    int updateByPrimaryKey(TiendaBean record);
}