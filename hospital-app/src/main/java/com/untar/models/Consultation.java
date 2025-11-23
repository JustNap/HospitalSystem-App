package com.untar.models;

public class Consultation {
    
    private int id;
    private int appointment_id;
    private String patient_name;
    private String date; 
    private String diagnosis;
    private String perception;
    private String create_at; 

    public Consultation() {} 
    
    // Getters
    public int getId() { return id; }
    public int getAppointment_id() { return appointment_id; }
    public String getPatient_name() { return patient_name; }
    public String getDate() { return date; }
    public String getDiagnosis() { return diagnosis; }
    public String getPerception() { return perception; } 
    public String getCreate_at() { return create_at; }

    // Setters (Opsional)
    public void setId(int id) { this.id = id; }
    public void setAppointment_id(int appointment_id) { this.appointment_id = appointment_id; }
    public void setPatient_name(String patient_name) { this.patient_name = patient_name; }
    public void setDate(String date) { this.date = date; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public void setPerception(String perception) { this.perception = perception; }
    public void setCreate_at(String create_at) { this.create_at = create_at; }
}