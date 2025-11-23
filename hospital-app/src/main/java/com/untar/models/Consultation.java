package com.untar.models;

public class Consultation {
    private int id;
    private String consultation_date;
    private String diagnosis;
    private String notes;
    private String doctorName;
    private String patientName;

    public Consultation(){
    }

    public int getId() {
        return id;
    }

    public String getConsultation_date(){
        return consultation_date;
    }

    public String getDiagnosis(){
        return diagnosis;
    }
    public String getNotes(){
        return notes;
    }
    public String getDoctorName(){
        return doctorName;
    }
    public String getPatientName() {
        return patientName;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setConsultation_date(String consultation_date){
        this.consultation_date = consultation_date;
    }

    public void setDiagnosis(String diagnosis){
        this.diagnosis = diagnosis;
    }

    public void setNotes(String notes){
        this.notes = notes;
    }

    public void setDoctorName(String doctorName){
        this.doctorName = doctorName;
    }

    public void setPatientName(String patientName){
        this.patientName = patientName;
    }
}