package com.untar.models;

public class Diagnosis {
    private String patientName;
    private String date;
    private String diagnosis; 
    private String prescription;

    // Constructor
    public Diagnosis(String patientName, String date, String diagnosis, String prescription) {
        this.patientName = patientName;
        this.date = date;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
    }

    // --- WAJIB ADA GETTER AGAR THYMELEAF BISA BACA ---
    public String getPatientName() { return patientName; }
    
    public String getDate() { return date; }
    
    public String getDiagnosis() { return diagnosis; } // Sesuai dengan ${diag.diagnosis}
    
    public String getPrescription() { return prescription; }
}