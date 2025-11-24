package com.untar.models;

public class Doctor {
    private int id;
    private String nama;
    private String spesialis;
    private String nomor_hp;

    public Doctor(int id, String nama, String spesialis, String nomor_hp) {
        this.id = id;
        this.nama = nama;
        this.spesialis = spesialis;
        this.nomor_hp = nomor_hp;
    }

    public int getId() { return id; }
    public String getNama() { return nama; }
    public String getSpesialis() { return spesialis; }
    public String getNomor_hp() { return nomor_hp; }
}