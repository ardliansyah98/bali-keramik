package com.btikk.balikeramik.models;

public class Balasan {
    private int id, id_komentar, id_user;
    private String nama, nama_perajin, foto_profil, tgl, balasan;

    public Balasan(int id, int id_komentar, int id_user, String nama, String nama_perajin, String foto_profil, String tgl, String balasan) {
        this.id = id;
        this.id_komentar = id_komentar;
        this.id_user = id_user;
        this.nama = nama;
        this.nama_perajin = nama_perajin;
        this.foto_profil = foto_profil;
        this.tgl = tgl;
        this.balasan = balasan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_komentar() {
        return id_komentar;
    }

    public void setId_komentar(int id_komentar) {
        this.id_komentar = id_komentar;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNama_perajin() {
        return nama_perajin;
    }

    public void setNama_perajin(String nama_perajin) {
        this.nama_perajin = nama_perajin;
    }

    public String getFoto_profil() {
        return foto_profil;
    }

    public void setFoto_profil(String foto_profil) {
        this.foto_profil = foto_profil;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

    public String getBalasan() {
        return balasan;
    }

    public void setBalasan(String balasan) {
        this.balasan = balasan;
    }
}
