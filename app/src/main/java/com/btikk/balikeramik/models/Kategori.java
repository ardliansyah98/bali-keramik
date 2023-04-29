package com.btikk.balikeramik.models;

public class Kategori {
    private int id;
    private String nama, gambar;

    public Kategori(int id, String nama, String gambar) {
        this.id = id;
        this.nama = nama;
        this.gambar = gambar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
