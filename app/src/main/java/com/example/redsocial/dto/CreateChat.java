package com.example.redsocial.dto;

public class CreateChat {

    private String nombreSala, idFrom, idTo;


    public CreateChat() {
    }

    public String getIdTo() {
        return idTo;
    }

    public void setIdTo(String idTo) {
        this.idTo = idTo;
    }

    public String getIdFrom() {
        return idFrom;
    }

    public void setIdFrom(String idFrom) {
        this.idFrom = idFrom;
    }

    public String getNombreSala() {
        return nombreSala;
    }

    public void setNombreSala(String nombreSala) {
        this.nombreSala = nombreSala;
    }

    @Override
    public String toString() {
        return "CreateChat{" +
                "nombreSala='" + nombreSala + '\'' +
                ", idFrom='" + idFrom + '\'' +
                ", idTo='" + idTo + '\'' +
                '}';
    }
}
