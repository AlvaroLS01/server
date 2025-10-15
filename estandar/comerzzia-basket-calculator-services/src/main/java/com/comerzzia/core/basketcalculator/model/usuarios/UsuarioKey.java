package com.comerzzia.core.basketcalculator.model.usuarios;

import com.comerzzia.core.basketcalculator.util.base.MantenimientoBean;

public class UsuarioKey extends MantenimientoBean {

	private static final long serialVersionUID = 3180369843972525987L;

	private String uidInstancia;

    private Long idUsuario;

    public String getUidInstancia() {
        return uidInstancia;
    }

    public void setUidInstancia(String uidInstancia) {
        this.uidInstancia = uidInstancia == null ? null : uidInstancia.trim();
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

	@Override
	protected void initNuevoBean() {
		
	}
}