package com.comerzzia.core.basketcalculator.persistencia.pasarelas.tipos;

import com.comerzzia.core.basketcalculator.util.base.ParametrosBuscarBean;

public class ParametrosBuscarTipoPasarelaBean extends ParametrosBuscarBean {

	private static final long serialVersionUID = 3458020718060146093L;

	private String idTipoPasarela;
	private String desTipoPasarela;

	public ParametrosBuscarTipoPasarelaBean() {
		super.setOrden("ID_TIPO_PASARELA");
	}

	public String getIdTipoPasarela() {
		return idTipoPasarela;
	}

	public void setIdTipoPasarela(String idTipoPasarela) {
		this.idTipoPasarela = idTipoPasarela;
	}

	public String getDesTipoPasarela() {
		return desTipoPasarela;
	}

	public void setDesTipoPasarela(String desTipoPasarela) {
		this.desTipoPasarela = desTipoPasarela;
	}

}