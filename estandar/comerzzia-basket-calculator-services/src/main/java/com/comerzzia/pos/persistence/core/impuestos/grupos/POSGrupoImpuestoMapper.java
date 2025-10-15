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
package com.comerzzia.pos.persistence.core.impuestos.grupos;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface POSGrupoImpuestoMapper {
    int countByExample(GrupoImpuestoExample example);

    int deleteByExample(GrupoImpuestoExample example);

    int deleteByPrimaryKey(GrupoImpuestoKey key);

    int insert(GrupoImpuestoBean record);

    int insertSelective(GrupoImpuestoBean record);

    List<GrupoImpuestoBean> selectByExampleWithRowbounds(GrupoImpuestoExample example, RowBounds rowBounds);

    List<GrupoImpuestoBean> selectByExample(GrupoImpuestoExample example);

    GrupoImpuestoBean selectByPrimaryKey(GrupoImpuestoKey key);

    int updateByExampleSelective(@Param("record") GrupoImpuestoBean record, @Param("example") GrupoImpuestoExample example);

    int updateByExample(@Param("record") GrupoImpuestoBean record, @Param("example") GrupoImpuestoExample example);

    int updateByPrimaryKeySelective(GrupoImpuestoBean record);

    int updateByPrimaryKey(GrupoImpuestoBean record);
}