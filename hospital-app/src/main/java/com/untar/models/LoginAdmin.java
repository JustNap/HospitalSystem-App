package com.untar.models;

public class LoginAdmin {
    private int id;
    private String nama;
    private int nomor_hp;

    public LoginAdmin() {} 

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public int getNomor_hp() { return nomor_hp; }
    public void setNomor_hp(int nomor_hp) { this.nomor_hp = nomor_hp; }
}