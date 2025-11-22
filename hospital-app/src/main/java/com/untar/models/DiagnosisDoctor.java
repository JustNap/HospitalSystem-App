package com.untar.models;
import java.sql.Date;

public class DiagnosisDoctor {
    private int id; 
    
    private int appointmentId;
    private String patientName;
    private int patientAge;
    private String gender;
    private Date date;
    private String diagnosis;
    private String prescription;

    public DiagnosisDoctor() {}

    // Constructor Lengkap (Untuk Simpan Data)
    public DiagnosisDoctor(int appointmentId, String patientName, int patientAge, String gender, Date date, String diagnosis, String prescription) {
        this.appointmentId = appointmentId;
        this.patientName = patientName;
        this.patientAge = patientAge;
        this.gender = gender;
        this.date = date;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getAppointmentId() { return appointmentId; }
    public void setAppointmentId(int appointmentId) { this.appointmentId = appointmentId; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public int getPatientAge() { return patientAge; }
    public void setPatientAge(int patientAge) { this.patientAge = patientAge; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }

    public String getPrescription() { return prescription; }
    public void setPrescription(String prescription) { this.prescription = prescription; }
}