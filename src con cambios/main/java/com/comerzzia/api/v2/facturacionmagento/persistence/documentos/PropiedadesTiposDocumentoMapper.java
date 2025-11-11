package com.comerzzia.api.v2.facturacionmagento.persistence.documentos;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.comerzzia.core.model.tiposdocumentos.prop.TipoDocumentoPropBean;

@Component
public interface PropiedadesTiposDocumentoMapper {

	public List<TipoDocumentoPropBean> selectById(@Param("uid_actividad") String uidActividad, @Param("idTipoDocumento") Long idTipoDocumento);
	
}
