package com.comerzzia.core.basketcalculator.model.usuarios;

import javax.xml.bind.annotation.XmlTransient;

public class UsuarioBean extends UsuarioKey {

	private static final long serialVersionUID = -8403076907368766716L;

	private String usuario;

    private String desUsuario;

    @XmlTransient
    private String clave;

    @XmlTransient
    private String uidMenuPorDefecto;

    @XmlTransient
    private Boolean puedeCambiarMenu;    

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario == null ? null : usuario.trim();
    }

    public String getDesUsuario() {
        return desUsuario;
    }

    public void setDesUsuario(String desUsuario) {
        this.desUsuario = desUsuario == null ? null : desUsuario.trim();
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave == null ? null : clave.trim();
    }

    public String getUidMenuPorDefecto() {
        return uidMenuPorDefecto;
    }

    public void setUidMenuPorDefecto(String uidMenuPorDefecto) {
        this.uidMenuPorDefecto = uidMenuPorDefecto == null ? null : uidMenuPorDefecto.trim();
    }

    public Boolean getPuedeCambiarMenu() {
        return puedeCambiarMenu;
    }

    public void setPuedeCambiarMenu(Boolean puedeCambiarMenu) {
        this.puedeCambiarMenu = puedeCambiarMenu;
    }
    
    //INICIO MÉTODOS PERSONALIZADOS--------------------------------------------
    
    @Override
    protected void initNuevoBean() {
	}
    
    public boolean isClaveValida(String clave) {
		return this.clave.equals(clave);
	}
	
	public String getPuedeCambiarMenuString() {
        return ((puedeCambiarMenu != null) && puedeCambiarMenu) ? "S" : "N";
    }
    
    //FIN MÉTODOS PERSONALIZADOS-----------------------------------------------

}