package com.comerzzia.pos.persistence.articulos.categorizaciones;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategorizacionBean implements Serializable {
	private static final long serialVersionUID = 664903431984951958L;
	
	private String uidActividad;
    private String codcat;
    private String descat;
    private String activo;
    private Long version;
    private Date fechaVersion;
}