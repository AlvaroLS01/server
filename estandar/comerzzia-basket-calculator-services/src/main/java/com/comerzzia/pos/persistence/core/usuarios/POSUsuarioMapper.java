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
package com.comerzzia.pos.persistence.core.usuarios;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface POSUsuarioMapper {
    int countByExample(UsuarioExample example);

    int deleteByExample(UsuarioExample example);

    int deleteByPrimaryKey(UsuarioKey key);

    int insert(UsuarioBean record);

    int insertSelective(UsuarioBean record);

    List<UsuarioBean> selectByExampleWithRowbounds(UsuarioExample example, RowBounds rowBounds);

    List<UsuarioBean> selectByExample(UsuarioExample example);

    UsuarioBean selectByPrimaryKey(UsuarioKey key);

    int updateByExampleSelective(@Param("record") UsuarioBean record, @Param("example") UsuarioExample example);

    int updateByExample(@Param("record") UsuarioBean record, @Param("example") UsuarioExample example);

    int updateByPrimaryKeySelective(UsuarioBean record);

    int updateByPrimaryKey(UsuarioBean record);
}