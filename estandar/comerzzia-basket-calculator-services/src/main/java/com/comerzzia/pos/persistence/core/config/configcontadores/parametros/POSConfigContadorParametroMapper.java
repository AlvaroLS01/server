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
package com.comerzzia.pos.persistence.core.config.configcontadores.parametros;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface POSConfigContadorParametroMapper {
    int countByExample(ConfigContadorParametroExample example);

    int deleteByExample(ConfigContadorParametroExample example);

    int deleteByPrimaryKey(ConfigContadorParametroKey key);

    int insert(ConfigContadorParametroBean record);

    int insertSelective(ConfigContadorParametroBean record);

    List<ConfigContadorParametroBean> selectByExampleWithRowbounds(ConfigContadorParametroExample example, RowBounds rowBounds);

    List<ConfigContadorParametroBean> selectByExample(ConfigContadorParametroExample example);

    ConfigContadorParametroBean selectByPrimaryKey(ConfigContadorParametroKey key);

    int updateByExampleSelective(@Param("record") ConfigContadorParametroBean record, @Param("example") ConfigContadorParametroExample example);

    int updateByExample(@Param("record") ConfigContadorParametroBean record, @Param("example") ConfigContadorParametroExample example);

    int updateByPrimaryKeySelective(ConfigContadorParametroBean record);

    int updateByPrimaryKey(ConfigContadorParametroBean record);
}