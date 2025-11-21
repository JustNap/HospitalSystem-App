package com.untar.services;

import java.util.List;

import org.sql2o.Sql2o;

import com.untar.config.Database;
import com.untar.models.Doctor;
import com.untar.repository.DoctorRepository;

public class DoctorService {

    private DoctorRepository doctorRepository;
    private Sql2o sql2o;

    public DoctorService() {
        this.sql2o = Database.getSql2o();
        this.doctorRepository = new DoctorRepository(this.sql2o);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public void addDoctor(String name, String speciality) {
        if (name == null || name.isEmpty() || speciality == null || speciality.isEmpty()) {
            return;
        }

        Doctor newDoctor = new Doctor(name, speciality);
        doctorRepository.save(newDoctor);
    }

    public void deleteDoctor(String id) {
        try {
            int doctorId = Integer.parseInt(id);
            doctorRepository.deleteById(doctorId);
        } catch (NumberFormatException e) {
            System.err.println("Error: ID dokter tidak valid (bukan angka): " + id);
        }
    }
}