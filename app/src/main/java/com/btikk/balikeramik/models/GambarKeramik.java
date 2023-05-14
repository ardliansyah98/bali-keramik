package com.btikk.balikeramik.models;

public class GambarKeramik {
    private int id, id_keramik;
    String gambar;

    public GambarKeramik(int id, int id_keramik, String gambar){
        this.id = id;
        this.id_keramik = id_keramik;
        this.gambar = gambar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_keramik() {
        return id_keramik;
    }

    public void setId_keramik(int id_keramik) {
        this.id_keramik = id_keramik;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
