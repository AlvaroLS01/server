package com.comerzzia.pos.services.core.sesion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StoreTillSessionKey {
   protected String uidInstancia;
   protected String uidActividad;
   protected String storeId;
   protected String tillId;
}
