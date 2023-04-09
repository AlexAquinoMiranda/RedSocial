package com.example.redsocial.dto;

public class Contacts {
    private String idContacto;
    private String idUsuarioLocal;

    public String getIdUsuarioLocal() {
        return idUsuarioLocal;
    }

    public void setIdUsuarioLocal(String idUsuarioLocal) {
        this.idUsuarioLocal = idUsuarioLocal;
    }

    public String getIdContacto() {
        return idContacto;
    }

    public Contacts() {
    }

    @Override
    public String toString() {
        return "Contacts{" +
                "idContacto='" + idContacto + '\'' +
                ", idUsuarioLocal='" + idUsuarioLocal + '\'' +
                '}';
    }

    public void setIdContacto(String idContacto) {
        this.idContacto = idContacto;
    }
}
