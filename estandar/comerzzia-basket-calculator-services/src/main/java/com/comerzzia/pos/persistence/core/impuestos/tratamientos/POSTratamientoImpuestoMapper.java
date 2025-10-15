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
package com.comerzzia.pos.persistence.core.impuestos.tratamientos;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface POSTratamientoImpuestoMapper {
    int countByExample(TratamientoImpuestoExample example);

    int deleteByExample(TratamientoImpuestoExample example);

    int deleteByPrimaryKey(TratamientoImpuestoKey key);

    int insert(TratamientoImpuestoBean record);

    int insertSelective(TratamientoImpuestoBean record);

    List<TratamientoImpuestoBean> selectByExampleWithRowbounds(TratamientoImpuestoExample example, RowBounds rowBounds);

    List<TratamientoImpuestoBean> selectByExample(TratamientoImpuestoExample example);

    TratamientoImpuestoBean selectByPrimaryKey(TratamientoImpuestoKey key);

    int updateByExampleSelective(@Param("record") TratamientoImpuestoBean record, @Param("example") TratamientoImpuestoExample example);

    int updateByExample(@Param("record") TratamientoImpuestoBean record, @Param("example") TratamientoImpuestoExample example);

    int updateByPrimaryKeySelective(TratamientoImpuestoBean record);

    int updateByPrimaryKey(TratamientoImpuestoBean record);
}