package com.btikk.balikeramik.models;

public class Events {
    private int id_event;
    private String judul, konten, gambar;

    public Events(int id_event, String judul, String konten, String gambar) {
        this.id_event = id_event;
        this.judul = judul;
        this.konten = konten;
        this.gambar = gambar;
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
}
