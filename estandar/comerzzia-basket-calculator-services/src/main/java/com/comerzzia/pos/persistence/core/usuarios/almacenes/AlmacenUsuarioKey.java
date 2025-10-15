package com.comerzzia.pos.persistence.core.usuarios.almacenes;

public class AlmacenUsuarioKey {
    private String uidActividad;

    private String codalm;

    private String uidInstancia;

    private Long idUsuario;

    public String getUidActividad() {
        return uidActividad;
    }

    public void setUidActividad(String uidActividad) {
        this.uidActividad = uidActividad == null ? null : uidActividad.trim();
    }

    public String getCodalm() {
        return codalm;
    }

    public void setCodalm(String codalm) {
        this.codalm = codalm == null ? null : codalm.trim();
    }

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
}