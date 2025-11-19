package com.untar.models;

public class Doctor {
    private int id;
    private String nama;
    private String spesialis;
    private int nomorHP;

    public Doctor(int id, String nama, String spesialis, int nomorHP) {
        this.id = id;
        this.nama = nama;
        this.spesialis = spesialis;
        this.nomorHP = nomorHP;
    }

    // Getter & Setter
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

    public String getSpesialis() {
        return spesialis;
    }

    public void setSpesialis(String spesialis) {
        this.spesialis = spesialis;
    }

    public int getNomorHP() {
        return nomorHP;
    }

    public void setNomorHP(int nomorHP) {
        this.nomorHP = nomorHP;
    }
}