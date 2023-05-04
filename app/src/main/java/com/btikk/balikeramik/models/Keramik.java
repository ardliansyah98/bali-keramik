package com.btikk.balikeramik.models;

public class Keramik {
    private int id, id_perajin;
    private String namaKeramik, dimensiKeramik, warnaKeramik, deskripsiKeramik, gambarKeramik, namaPerajin, fotoPerajin, kategoriKeramik;

    public Keramik(int id, int id_perajin, String namaKeramik, String dimensiKeramik, String warnaKeramik, String deskripsiKeramik, String gambarKeramik, String namaPerajin, String fotoPerajin, String kategoriKeramik){
        this.id = id;
        this.id_perajin = id_perajin;
        this.namaKeramik = namaKeramik;
        this.dimensiKeramik = dimensiKeramik;
        this.warnaKeramik = warnaKeramik;
        this.deskripsiKeramik = deskripsiKeramik;
        this.gambarKeramik = gambarKeramik;
        this.namaPerajin = namaPerajin;
        this.fotoPerajin = fotoPerajin;
        this.kategoriKeramik = kategoriKeramik;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_perajin() {
        return id_perajin;
    }

    public void setId_perajin(int id_perajin) {
        this.id_perajin = id_perajin;
    }

    public String getNamaKeramik() {
        return namaKeramik;
    }

    public void setNamaKeramik(String namaKeramik) {
        this.namaKeramik = namaKeramik;
    }

    public String getDimensiKeramik() {
        return dimensiKeramik;
    }

    public void setDimensiKeramik(String dimensiKeramik) {
        this.dimensiKeramik = dimensiKeramik;
    }

    public String getWarnaKeramik() {
        return warnaKeramik;
    }

    public void setWarnaKeramik(String warnaKeramik) {
        this.warnaKeramik = warnaKeramik;
    }

    public String getDeskripsiKeramik() {
        return deskripsiKeramik;
    }

    public void setDeskripsiKeramik(String deskripsiKeramik) {
        this.deskripsiKeramik = deskripsiKeramik;
    }

    public String getGambarKeramik() {
        return gambarKeramik;
    }

    public void setGambarKeramik(String gambarKeramik) {
        this.gambarKeramik = gambarKeramik;
    }

    public String getNamaPerajin() {
        return namaPerajin;
    }

    public void setNamaPerajin(String namaPerajin) {
        this.namaPerajin = namaPerajin;
    }

    public String getFotoPerajin() {
        return fotoPerajin;
    }

    public void setFotoPerajin(String fotoPerajin) {
        this.fotoPerajin = fotoPerajin;
    }

    public String getKategoriKeramik() {
        return kategoriKeramik;
    }

    public void setKategoriKeramik(String kategoriKeramik) {
        this.kategoriKeramik = kategoriKeramik;
    }
}

