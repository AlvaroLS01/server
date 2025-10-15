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
package com.comerzzia.pos.persistence.core.empresas;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface POSEmpresaMapper {
    int countByExample(EmpresaExample example);

    int deleteByExample(EmpresaExample example);

    int deleteByPrimaryKey(EmpresaKey key);

    int insert(EmpresaBean record);

    int insertSelective(EmpresaBean record);

    List<EmpresaBean> selectByExampleWithBLOBsWithRowbounds(EmpresaExample example, RowBounds rowBounds);

    List<EmpresaBean> selectByExampleWithBLOBs(EmpresaExample example);

    List<EmpresaBean> selectByExampleWithRowbounds(EmpresaExample example, RowBounds rowBounds);

    List<EmpresaBean> selectByExample(EmpresaExample example);

    EmpresaBean selectByPrimaryKey(EmpresaKey key);

    int updateByExampleSelective(@Param("record") EmpresaBean record, @Param("example") EmpresaExample example);

    int updateByExampleWithBLOBs(@Param("record") EmpresaBean record, @Param("example") EmpresaExample example);

    int updateByExample(@Param("record") EmpresaBean record, @Param("example") EmpresaExample example);

    int updateByPrimaryKeySelective(EmpresaBean record);

    int updateByPrimaryKeyWithBLOBs(EmpresaBean record);

    int updateByPrimaryKey(EmpresaBean record);
}