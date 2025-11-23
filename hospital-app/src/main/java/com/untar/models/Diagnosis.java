package com.untar.models;

public class Diagnosis {
    private String patientName;
    private String date;
    private String diagnosis; 
    private String prescription;

    public Diagnosis(String patientName, String date, String diagnosis, String prescription) {
        this.patientName = patientName;
        this.date = date;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
    }

    public String getPatientName() { return patientName; }
    
    public String getDate() { return date; }
    
    public String getDiagnosis() { return diagnosis; }
    
    public String getPrescription() { return prescription; }
}