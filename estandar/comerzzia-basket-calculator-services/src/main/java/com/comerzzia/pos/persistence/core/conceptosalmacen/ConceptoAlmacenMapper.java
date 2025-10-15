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
package com.comerzzia.pos.persistence.core.conceptosalmacen;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ConceptoAlmacenMapper {
    int countByExample(ConceptoAlmacenExample example);

    int deleteByExample(ConceptoAlmacenExample example);

    int deleteByPrimaryKey(ConceptoAlmacenKey key);

    int insert(ConceptoAlmacenBean record);

    int insertSelective(ConceptoAlmacenBean record);

    List<ConceptoAlmacenBean> selectByExampleWithRowbounds(ConceptoAlmacenExample example, RowBounds rowBounds);

    List<ConceptoAlmacenBean> selectByExample(ConceptoAlmacenExample example);

    ConceptoAlmacenBean selectByPrimaryKey(ConceptoAlmacenKey key);

    int updateByExampleSelective(@Param("record") ConceptoAlmacenBean record, @Param("example") ConceptoAlmacenExample example);

    int updateByExample(@Param("record") ConceptoAlmacenBean record, @Param("example") ConceptoAlmacenExample example);

    int updateByPrimaryKeySelective(ConceptoAlmacenBean record);

    int updateByPrimaryKey(ConceptoAlmacenBean record);
}