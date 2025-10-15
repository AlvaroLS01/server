package com.comerzzia.omnichannel.service.salesdocument.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.omnichannel.domain.dto.item.ItemDTO;
import com.comerzzia.omnichannel.model.documents.sales.generic.LineaDocumentoVentaArticulo;
import com.comerzzia.omnichannel.model.documents.sales.ticket.lineas.LineaTicketArticulo;
import com.comerzzia.pos.persistence.articulos.categorizaciones.CategorizacionBean;
import com.comerzzia.pos.persistence.articulos.categorizaciones.CategorizacionMapper;

@Service
public class ItemDTOConverter {
    @Autowired
    CategorizacionMapper categorizacionMapper;

    public LineaDocumentoVentaArticulo convertToLineaDocumentoVentaArticulo(final IDatosSesion datosSesion, ItemDTO item) {
        LineaDocumentoVentaArticulo result = new LineaDocumentoVentaArticulo();
        result.setDesArticulo(item.getItemDes());
        result.setFormato(item.getFormat());
        result.setCodFamilia(item.getFamilyCode());
        result.setCodseccion(item.getSectionCode());
        result.setCodCategorizacion(item.getCategoryCode());
        result.setCodProveedor(item.getSupplierCode());
        result.setReferenciaProveedor(item.getSupplierReference());
        result.setCodFabricante(item.getManufacturerCode());
        result.setCodImpuesto(item.getTaxTypeCode());
        result.setObservaciones(item.getComments());
        result.setActivo(item.getActive());
        result.setNumerosSerie(item.getSerialNumbersActive());
        result.setDesglose1(item.getCombination1Active());
        result.setDesglose2(item.getCombination2Active());
        result.setGenerico(item.getGenericItem());
        result.setEscaparate(item.getFeatured());
        result.setUnidadMedidaAlternativa(item.getUnitMeasureCodeAlt());
        result.setCodUmEtiqueta(item.getLabelUnitMeasureCode());
        result.setCantidadUmEtiqueta(item.getLabelUnitMeasureQuantity());
        result.setFechaAlta(item.getCreationDate());
        result.setCodMarca(item.getBrandCode());
        result.setBalanzaPlu(item.getScalePlu());
        result.setBalanzaSeccion(item.getScaleSection());
        result.setBalanzaTipoArticulo(item.getScaleItemType());
        result.setIdTipoSustitucion(item.getReplacementTypeId());
        // lineaDocumentoVentaArticulo.setDesTipoSustitucion(articulo.getReplacementTypeId());
        // lineaDocumentoVentaArticulo.setConfirmarPrecioVenta(articulo.getConfirmSalesPrice());
        
        if (item.getTags() != null && item.getTags().size() > 0) {
            for (String tagUid : item.getTags()) {
                result.addLineaTicketArticuloEtiqueta(tagUid, tagUid);
            }            
        }

        CategorizacionBean categorizacion = categorizacionMapper.selectByPrimaryKey(datosSesion.getUidActividad(), item.getCategoryCode());

        if (categorizacion != null) {
            result.setDesCategorizacion(categorizacion.getDescat());
        }

        return result;
    }
    
    public LineaTicketArticulo convertToLineaTicketArticulo(final IDatosSesion datosSesion, ItemDTO item) {
        LineaTicketArticulo result = new LineaTicketArticulo();
        result.setDesArticulo(item.getItemDes());
        result.setFormato(item.getFormat());
        result.setCodFamilia(item.getFamilyCode());
        result.setCodseccion(item.getSectionCode());
        result.setCodCategorizacion(item.getCategoryCode());
        result.setCodProveedor(item.getSupplierCode());
        result.setReferenciaProveedor(item.getSupplierReference());
        result.setCodFabricante(item.getManufacturerCode());
        result.setCodImpuesto(item.getTaxTypeCode());
        result.setObservaciones(item.getComments());
        result.setActivo(item.getActive());
        result.setNumerosSerie(item.getSerialNumbersActive());
        result.setDesglose1(item.getCombination1Active());
        result.setDesglose2(item.getCombination2Active());
        result.setGenerico(item.getGenericItem());
        result.setEscaparate(item.getFeatured());
        result.setUnidadMedidaAlternativa(item.getUnitMeasureCodeAlt());
        result.setCodUmEtiqueta(item.getLabelUnitMeasureCode());
        result.setCantidadUmEtiqueta(item.getLabelUnitMeasureQuantity());
        result.setFechaAlta(item.getCreationDate());
        result.setCodMarca(item.getBrandCode());
        result.setBalanzaPlu(item.getScalePlu());
        result.setBalanzaSeccion(item.getScaleSection());
        result.setBalanzaTipoArticulo(item.getScaleItemType());
        result.setIdTipoSustitucion(item.getReplacementTypeId());
        // lineaDocumentoVentaArticulo.setDesTipoSustitucion(articulo.getReplacementTypeId());
        // lineaDocumentoVentaArticulo.setConfirmarPrecioVenta(articulo.getConfirmSalesPrice());
        
        if (item.getTags() != null && item.getTags().size() > 0) {
            for (String tagUid : item.getTags()) {
                result.addLineaTicketArticuloEtiqueta(tagUid, tagUid);
            }            
        }

        return result;
    }
}
