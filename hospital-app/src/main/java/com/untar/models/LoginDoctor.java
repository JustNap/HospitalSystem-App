package com.untar.models;

public class LoginDoctor {
    private int id;
    private String nama;
    private String spesialis;
    private int nomor_hp;

    public LoginDoctor() {} // Wajib kosong

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getSpesialis() { return spesialis; }
    public void setSpesialis(String spesialis) { this.spesialis = spesialis; }
    public int getNomor_hp() { return nomor_hp; }
    public void setNomor_hp(int nomor_hp) { this.nomor_hp = nomor_hp; }
}