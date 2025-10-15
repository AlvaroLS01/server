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
package com.comerzzia.pos.persistence.core.documentos.tipos;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface POSTipoDocumentoMapper {
    int countByExample(TipoDocumentoExample example);

    int deleteByExample(TipoDocumentoExample example);

    int deleteByPrimaryKey(TipoDocumentoKey key);

    int insert(TipoDocumentoBean record);

    int insertSelective(TipoDocumentoBean record);

    List<TipoDocumentoBean> selectByExampleWithRowbounds(TipoDocumentoExample example, RowBounds rowBounds);

    List<TipoDocumentoBean> selectByExample(TipoDocumentoExample example);

    TipoDocumentoBean selectByPrimaryKey(TipoDocumentoKey key);

    int updateByExampleSelective(@Param("record") TipoDocumentoBean record, @Param("example") TipoDocumentoExample example);

    int updateByExample(@Param("record") TipoDocumentoBean record, @Param("example") TipoDocumentoExample example);

    int updateByPrimaryKeySelective(TipoDocumentoBean record);

    int updateByPrimaryKey(TipoDocumentoBean record);
}