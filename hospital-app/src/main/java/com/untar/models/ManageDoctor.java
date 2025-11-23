package com.untar.models;

public class ManageDoctor {
    private int id;
    private String name;
    private String speciality;
    private String phoneNumber;

    public ManageDoctor(){
    }

    public ManageDoctor(String name, String speciality) {
        this.name = name;
        this.speciality = speciality;
        this.phoneNumber = "-";
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

    public String getPhoneNumber(){
        return phoneNumber;
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

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
}