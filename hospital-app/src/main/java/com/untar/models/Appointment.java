package com.untar.models;

public class Appointment {
    private int id;
    private String dokter;    
    private String spesialis; 
    private String tanggal;  
    private String waktu;

    public Appointment() {
    }

    public Appointment(int id, String dokter, String spesialis, String tanggal, String waktu) {
        this.id = id;
        this.dokter = dokter;
        this.spesialis = spesialis;
        this.tanggal = tanggal;
        this.waktu = waktu;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDokter() { return dokter; }
    public void setDokter(String dokter) { this.dokter = dokter; }

    public String getSpesialis() { return spesialis; }
    public void setSpesialis(String spesialis) { this.spesialis = spesialis; }

    public String getTanggal() { return tanggal; }
    public void setTanggal(String tanggal) { this.tanggal = tanggal; }

    public String getWaktu() { return waktu; }
    public void setWaktu(String waktu) { this.waktu = waktu; }
}