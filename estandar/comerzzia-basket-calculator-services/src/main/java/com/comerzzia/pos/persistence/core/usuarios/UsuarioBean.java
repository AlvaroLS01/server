/**
 * ComerZZia 3.0
 *
 * Copyright (c) 2008-2015 Comerzzia, S.L.  All Rights Reserved.
 *
 * THIS WORK IS  SUBJECT  TO  SPAIN  AND  INTERNATIONAL  COPYRIGHT  LAWS  AND
 * TREATIES.   NO  PART  OF  THIS  WORK MAY BE  USED,  PRACTICED,  PERFORMED
 * COPIED, DISTRIBUTED, REVISED, MODIFIED, TRANSLATED,  ABRIDGED, CONDENSED,
 * EXPANDED,  COLLECTED,  COMPILED,  LINKED,  RECAST, TRANSFORMED OR ADAPTED
 * WITHOUT THE PRIOR WRITTEN CONSENT OF COMERZZIA, S.L. ANY USE OR EXPLOITATION
 * OF THIS WORK WITHOUT AUTHORIZATION COULD SUBJECT THE PERPETRATOR TO
 * CRIMINAL AND CIVIL LIABILITY.
 *
 * CONSULT THE END USER LICENSE AGREEMENT FOR INFORMATION ON ADDITIONAL
 * RESTRICTIONS.
 */
package com.comerzzia.pos.persistence.core.usuarios;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public class UsuarioBean extends UsuarioKey {
    @XmlElement
    private String usuario;

    @XmlElement
    private String desusuario;

    private String clave;

    private Boolean activo;

    private String menuPorDefecto;

    private String puedeCambiarMenu;
    
    //INICIO ATRIBUTOS PERSONALIZADOS--------------------------------------------
    
    //FIN ATRIBUTOS PERSONALIZADOS-----------------------------------------------


    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario == null ? null : usuario.trim();
    }

    public String getDesusuario() {
        return desusuario;
    }

    public void setDesusuario(String desusuario) {
        this.desusuario = desusuario == null ? null : desusuario.trim();
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave == null ? null : clave.trim();
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getMenuPorDefecto() {
        return menuPorDefecto;
    }

    public void setMenuPorDefecto(String menuPorDefecto) {
        this.menuPorDefecto = menuPorDefecto == null ? null : menuPorDefecto.trim();
    }

    public String getPuedeCambiarMenu() {
        return puedeCambiarMenu;
    }

    public void setPuedeCambiarMenu(String puedeCambiarMenu) {
        this.puedeCambiarMenu = puedeCambiarMenu == null ? null : puedeCambiarMenu.trim();
    }
    
    //INICIO MÉTODOS PERSONALIZADOS--------------------------------------------
    @Override
    public String toString() {
        return desusuario;
    }
    //FIN MÉTODOS PERSONALIZADOS-----------------------------------------------

}