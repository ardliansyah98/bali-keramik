package com.btikk.balikeramik.models;

public class Komentar {
    private int id, id_akun, id_keramik;
    private String komentar, date_created, nama, foto_profil, nama_perajin;

    public Komentar(int id, int id_akun, int id_keramik, String komentar,
                    String date_created, String nama, String foto_profil,
                    String nama_perajin) {
        this.id = id;
        this.id_akun = id_akun;
        this.id_keramik = id_keramik;
        this.komentar = komentar;
        this.date_created = date_created;
        this.nama = nama;
        this.foto_profil = foto_profil;
        this.nama_perajin = nama_perajin;
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

    public int getId_keramik() {
        return id_keramik;
    }

    public void setId_keramik(int id_keramik) {
        this.id_keramik = id_keramik;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getFoto_profil() {
        return foto_profil;
    }

    public void setFoto_profil(String foto_profil) {
        this.foto_profil = foto_profil;
    }

    public String getNama_perajin() {
        return nama_perajin;
    }

    public void setNama_perajin(String nama_perajin) {
        this.nama_perajin = nama_perajin;
    }
}
