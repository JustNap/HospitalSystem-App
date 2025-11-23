package com.untar.models;

public class Appointment {
    private int id;
    private String dokter;
    private String spesialis;
    private String tanggal;
    private String waktu;

    public Appointment(int id, String dokter, String spesialis, String tanggal, String waktu) {
        this.id = id;
        this.dokter = dokter;
        this.spesialis = spesialis;
        this.tanggal = tanggal;
        this.waktu = waktu;
    }

    public int getId() { return id; }
    public String getDokter() { return dokter; }
    public String getSpesialis() { return spesialis; }
    public String getTanggal() { return tanggal; }
    public String getWaktu() { return waktu; }
}