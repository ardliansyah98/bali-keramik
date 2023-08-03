package com.btikk.balikeramik.models;

public class Events {
    private int id_event;
    private String judul, konten, gambar, date;

    public Events(int id_event, String judul, String konten, String gambar, String date) {
        this.id_event = id_event;
        this.judul = judul;
        this.konten = konten;
        this.gambar = gambar;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId_event() {
        return id_event;
    }

    public String getJudul() {
        return judul;
    }

    public String getKonten() {
        return konten;
    }

    public String getGambar() {
        return gambar;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public void setKonten(String konten) {
        this.konten = konten;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
