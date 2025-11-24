package com.untar.models;

public class LoginPatient {
    private int id;
    private String nama;
    private String email;
    private String password;
    private String nomor_hp; 

    public LoginPatient() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNomor_hp() { return nomor_hp; }
    public void setNomor_hp(String nomor_hp) { this.nomor_hp = nomor_hp; }
}