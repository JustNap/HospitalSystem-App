package com.untar.models;
import java.sql.Timestamp;

public class DoctorAppointment {
    private int id;
    private String patient_name;
    private String time;
    private String complaint;
    private Timestamp created_at;

    public DoctorAppointment() {}

    public DoctorAppointment(int id, String patient_name, String time, String complaint, Timestamp created_at) {
        this.id = id;
        this.patient_name = patient_name;
        this.time = time;
        this.complaint = complaint;
        this.created_at = created_at;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPatient_name() { return patient_name; }
    public void setPatient_name(String patient_name) { this.patient_name = patient_name; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getComplaint() { return complaint; }
    public void setComplaint(String complaint) { this.complaint = complaint; }

    public Timestamp getCreated_at() { return created_at; }
    public void setCreated_at(Timestamp created_at) { this.created_at = created_at; }
}