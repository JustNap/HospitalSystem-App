package com.untar.models;

public class Doctor {
    private int id;
    private String name;
    private String speciality;
    public Doctor(){
    }

    public Doctor(String name, String speciality) {
        this.name = name;
        this.speciality = speciality;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getSpeciality(){
        return speciality;
    }

    public void setId(int id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}