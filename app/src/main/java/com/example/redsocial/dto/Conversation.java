package com.example.redsocial.dto;

import java.io.Serializable;

public class Conversation implements Serializable {
    private String idSala, idFrom, idTo, fecha, text;

    public Conversation() {
    }

    public String getIdTo() {
        return idTo;
    }

    public void setIdTo(String idTo) {
        this.idTo = idTo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getIdFrom() {
        return idFrom;
    }

    public void setIdFrom(String idFrom) {
        this.idFrom = idFrom;
    }

    public String getIdSala() {
        return idSala;
    }

    public void setIdSala(String idSala) {
        this.idSala = idSala;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "idSala='" + idSala + '\'' +
                ", idFrom='" + idFrom + '\'' +
                ", idTo='" + idTo + '\'' +
                ", fecha='" + fecha + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
