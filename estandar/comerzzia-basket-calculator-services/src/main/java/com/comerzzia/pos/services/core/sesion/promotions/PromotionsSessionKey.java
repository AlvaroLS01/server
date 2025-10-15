package com.comerzzia.pos.services.core.sesion.promotions;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PromotionsSessionKey {
   protected String uidInstancia;
   protected String uidActividad;
   protected String storeId;
   protected Date vigence;
   protected String storeLanguageCode;
}
