package com.untar.models;

public class Appointment {
    public int id;
    public String namaDokter;
    public String spesialis;
    public String date;
    public String time;

    public Appointment(int id, String namaDokter, String spesialis, String date, String time) {
        this.id = id;
        this.namaDokter = namaDokter;
        this.spesialis = spesialis;
        this.date = date;
        this.time = time;
    }
}