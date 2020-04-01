package com.example.finalproyect_allengram.ModeloDatos;

import java.util.Date;

public class Publicacion {
    private long date;
    private String url_storage;

    public Publicacion(long date, String url_storage) {
        this.date = date;
        this.url_storage = url_storage;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getUrl_storage() {
        return url_storage;
    }

    public void setUrl_storage(String url_storage) {
        this.url_storage = url_storage;
    }

    @Override
    public String toString() {
        return "Publicacion{" +
                "date=" + date +
                ", url_storage='" + url_storage + '\'' +
                '}';
    }
}
