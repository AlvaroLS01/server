/**
 * ComerZZia 3.0 Copyright (c) 2008-2015 Comerzzia, S.L. All Rights Reserved. THIS WORK IS SUBJECT TO SPAIN AND
 * INTERNATIONAL COPYRIGHT LAWS AND TREATIES. NO PART OF THIS WORK MAY BE USED, PRACTICED, PERFORMED COPIED,
 * DISTRIBUTED, REVISED, MODIFIED, TRANSLATED, ABRIDGED, CONDENSED, EXPANDED, COLLECTED, COMPILED, LINKED, RECAST,
 * TRANSFORMED OR ADAPTED WITHOUT THE PRIOR WRITTEN CONSENT OF COMERZZIA, S.L. ANY USE OR EXPLOITATION OF THIS WORK
 * WITHOUT AUTHORIZATION COULD SUBJECT THE PERPETRATOR TO CRIMINAL AND CIVIL LIABILITY. CONSULT THE END USER LICENSE
 * AGREEMENT FOR INFORMATION ON ADDITIONAL RESTRICTIONS.
 */
package com.comerzzia.pos.persistence.core.config.configcontadores;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface POSConfigContadorMapper {

	int countByExample(ConfigContadorExample example);

	int deleteByExample(ConfigContadorExample example);

	int deleteByPrimaryKey(String idContador);

	int insert(ConfigContadorBean record);

	int insertSelective(ConfigContadorBean record);

	List<ConfigContadorBean> selectByExampleWithRowbounds(ConfigContadorExample example, RowBounds rowBounds);

	List<ConfigContadorBean> selectByExample(ConfigContadorExample example);

	ConfigContadorBean selectByPrimaryKey(String idContador);

	int updateByExampleSelective(@Param("record") ConfigContadorBean record, @Param("example") ConfigContadorExample example);

	int updateByExample(@Param("record") ConfigContadorBean record, @Param("example") ConfigContadorExample example);

	int updateByPrimaryKeySelective(ConfigContadorBean record);

	int updateByPrimaryKey(ConfigContadorBean record);
}