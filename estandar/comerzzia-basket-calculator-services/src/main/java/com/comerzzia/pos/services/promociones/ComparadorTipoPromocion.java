package com.comerzzia.pos.services.promociones;

import java.util.Comparator;


public class ComparadorTipoPromocion implements Comparator<Promocion> {

	@Override
    public int compare(Promocion o1, Promocion o2) {
	    return o2.getIdTipoPromocion().compareTo(o1.getIdTipoPromocion());
    }

}
