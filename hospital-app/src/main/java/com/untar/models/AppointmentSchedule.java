package com.untar.models;

import java.sql.Date;
import java.sql.Time;

public class AppointmentSchedule {

    private int id;
    private int id_dokter;
    private Date tanggal;
    private Time waktu_mulai;
    private Time waktu_selesai;
    private int slot_total;
    private int slot_sisa;
    private String nama_dokter;

    // GETTERS
    public int getId() { return id; }
    public int getId_dokter() { return id_dokter; }
    public Date getTanggal() { return tanggal; }
    public Time getWaktu_mulai() { return waktu_mulai; }
    public Time getWaktu_selesai() { return waktu_selesai; }
    public int getSlot_total() { return slot_total; }
    public int getSlot_sisa() { return slot_sisa; }
    public String getNama_dokter() { return nama_dokter; }

    // SETTERS → PENTING AGAR DAO BISA ISI DATA
    public void setId(int id) { this.id = id; }
    public void setId_dokter(int id_dokter) { this.id_dokter = id_dokter; }
    public void setTanggal(Date tanggal) { this.tanggal = tanggal; }
    public void setWaktu_mulai(Time waktu_mulai) { this.waktu_mulai = waktu_mulai; }
    public void setWaktu_selesai(Time waktu_selesai) { this.waktu_selesai = waktu_selesai; }
    public void setSlot_total(int slot_total) { this.slot_total = slot_total; }
    public void setSlot_sisa(int slot_sisa) { this.slot_sisa = slot_sisa; }
    public void setNama_dokter(String nama_dokter) { this.nama_dokter = nama_dokter; }
}
