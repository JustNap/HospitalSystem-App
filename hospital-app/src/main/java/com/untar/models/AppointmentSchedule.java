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


    public int getId() { return id; }
    public int getId_dokter() { return id_dokter; }
    public Date getTanggal() { return tanggal; }
    public Time getWaktu_mulai() { return waktu_mulai; }
    public Time getWaktu_selesai() { return waktu_selesai; }
    public int getSlot_total() { return slot_total; }
    public int getSlot_sisa() { return slot_sisa; }
    public String getNama_dokter() { return nama_dokter; }
}
