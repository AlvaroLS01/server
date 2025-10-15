package com.comerzzia.pos.persistence.core.config.configcontadores.rangos;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface POSConfigContadorRangoMapper {

	int countByExample(ConfigContadorRangoExample example);

	int deleteByExample(ConfigContadorRangoExample example);

	int deleteByPrimaryKey(ConfigContadorRangoKey key);

	int insert(ConfigContadorRangoBean record);

	int insertSelective(ConfigContadorRangoBean record);

	List<ConfigContadorRangoBean> selectByExampleWithRowbounds(ConfigContadorRangoExample example, RowBounds rowBounds);

	List<ConfigContadorRangoBean> selectByExample(ConfigContadorRangoExample example);

	List<ConfigContadorRangoBean> selectByExampleOrNull(ConfigContadorRangoExample example);

	ConfigContadorRangoBean selectByPrimaryKey(ConfigContadorRangoKey key);

	int updateByExampleSelective(@Param("record") ConfigContadorRangoBean record, @Param("example") ConfigContadorRangoExample example);

	int updateByExample(@Param("record") ConfigContadorRangoBean record, @Param("example") ConfigContadorRangoExample example);

	int updateByPrimaryKeySelective(ConfigContadorRangoBean record);

	int updateByPrimaryKey(ConfigContadorRangoBean record);
}