package com.untar.models;

public class ManageDoctor {
    private int id;
    private String name;
    private String speciality;
    private String phoneNumber;
    private String password;

    public ManageDoctor(){
    }

    public ManageDoctor(String name, String speciality) {
        this.name = name;
        this.speciality = speciality;
        this.phoneNumber = "-";
        this.password = "1234";
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

    public String getPassword(){
        return password;
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

    public void setPassword(String password){
        this.password = password;
    }
}