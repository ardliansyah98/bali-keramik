package com.btikk.balikeramik.models;

public class User {
    private int id, id_perajin, is_active;
    String nama, email, no_telp, fotoProfil, dateCreated;

    public User(int id, int id_perajin, int is_active, String nama, String email, String no_telp, String fotoProfil, String dateCreated) {
        this.id = id;
        this.id_perajin = id_perajin;
        this.is_active = is_active;
        this.nama = nama;
        this.email = email;
        this.no_telp = no_telp;
        this.fotoProfil = fotoProfil;
        this.dateCreated = dateCreated;
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

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public String getFotoProfil() {
        return fotoProfil;
    }

    public void setFotoProfil(String fotoProfil) {
        this.fotoProfil = fotoProfil;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
