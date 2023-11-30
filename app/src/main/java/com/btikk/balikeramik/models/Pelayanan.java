package com.btikk.balikeramik.models;

public class Pelayanan {
    private int id_pelayanan;
    private String judul, konten, gambar, date;

    public Pelayanan(int id_pelayanan, String judul, String konten, String gambar, String date) {
        this.id_pelayanan = id_pelayanan;
        this.judul = judul;
        this.konten = konten;
        this.gambar = gambar;
        this.date = date;
    }

    public int getId_pelayanan() {
        return id_pelayanan;
    }

    public void setId_pelayanan(int id_pelayanan) {
        this.id_pelayanan = id_pelayanan;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getKonten() {
        return konten;
    }

    public void setKonten(String konten) {
        this.konten = konten;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
