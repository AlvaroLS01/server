package com.comerzzia.pos.persistence.promociones;

import java.util.List;

public interface PromocionMapper {
    List<PromocionBean> selectByCodAlmacen(PromocionBean promocion);
    PromocionBean selectByPrimaryKey(PromocionKey key);
    
    Integer checkPromotionsChange(PromocionBean promocion);
    
}