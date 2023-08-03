package com.btikk.balikeramik.models;

public class Perajin {
    int id, id_akun;
    String nama_perajin, deskripsi_perajin, alamat, nama_pemilik, email, foto_profil, date_created;

    public Perajin(int id, int id_akun, String nama_perajin, String deskripsi_perajin,
                   String alamat, String nama_pemilik, String email, String foto_profil, String date_created) {
        this.id = id;
        this.id_akun = id_akun;
        this.nama_perajin = nama_perajin;
        this.deskripsi_perajin = deskripsi_perajin;
        this.alamat = alamat;
        this.nama_pemilik = nama_pemilik;
        this.email = email;
        this.foto_profil = foto_profil;
        this.date_created = date_created;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_akun() {
        return id_akun;
    }

    public void setId_akun(int id_akun) {
        this.id_akun = id_akun;
    }

    public String getNama_perajin() {
        return nama_perajin;
    }

    public void setNama_perajin(String nama_perajin) {
        this.nama_perajin = nama_perajin;
    }

    public String getDeskripsi_perajin() {
        return deskripsi_perajin;
    }

    public void setDeskripsi_perajin(String deskripsi_perajin) {
        this.deskripsi_perajin = deskripsi_perajin;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNama_pemilik() {
        return nama_pemilik;
    }

    public void setNama_pemilik(String nama_pemilik) {
        this.nama_pemilik = nama_pemilik;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto_profil() {
        return foto_profil;
    }

    public void setFoto_profil(String foto_profil) {
        this.foto_profil = foto_profil;
    }
}
