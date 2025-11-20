package com.untar.models;
import java.sql.Timestamp;

public class DoctorAppointment {
    private int id;
    private String patientName;
    private String time;
    private String complaint;
    private Timestamp createdAt;

    // 1. Constructor Kosong (WAJIB ADA UNTUK ROUTE MANUAL)
    // Tanpa ini, baris "new DoctorAppointment()" di Controller akan error/gagal.
    public DoctorAppointment() {}

    // 2. Constructor Lengkap (Untuk Database)
    public DoctorAppointment(int id, String patientName, String time, String complaint, Timestamp createdAt) {
        this.id = id;
        this.patientName = patientName;
        this.time = time;
        this.complaint = complaint;
        this.createdAt = createdAt;
    }

    // 3. GETTER & SETTER (WAJIB LENGKAP)
    // Tanpa ini, HTML error "source is null"
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getComplaint() { return complaint; }
    public void setComplaint(String complaint) { this.complaint = complaint; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}