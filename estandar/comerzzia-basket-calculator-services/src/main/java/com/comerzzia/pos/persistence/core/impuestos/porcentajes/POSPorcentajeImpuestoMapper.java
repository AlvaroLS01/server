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
package com.comerzzia.pos.persistence.core.impuestos.porcentajes;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface POSPorcentajeImpuestoMapper {
    int countByExample(PorcentajeImpuestoExample example);

    int deleteByExample(PorcentajeImpuestoExample example);

    int deleteByPrimaryKey(PorcentajeImpuestoKey key);

    int insert(PorcentajeImpuestoBean record);

    int insertSelective(PorcentajeImpuestoBean record);

    List<PorcentajeImpuestoBean> selectByExampleWithRowbounds(PorcentajeImpuestoExample example, RowBounds rowBounds);

    List<PorcentajeImpuestoBean> selectByExample(PorcentajeImpuestoExample example);

    PorcentajeImpuestoBean selectByPrimaryKey(PorcentajeImpuestoKey key);

    int updateByExampleSelective(@Param("record") PorcentajeImpuestoBean record, @Param("example") PorcentajeImpuestoExample example);

    int updateByExample(@Param("record") PorcentajeImpuestoBean record, @Param("example") PorcentajeImpuestoExample example);

    int updateByPrimaryKeySelective(PorcentajeImpuestoBean record);

    int updateByPrimaryKey(PorcentajeImpuestoBean record);
}