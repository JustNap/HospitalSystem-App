package com.untar.services;

import java.util.List;

import org.sql2o.Sql2o;

import com.untar.config.Database;
import com.untar.models.ManageDoctor;
import com.untar.repository.ManageDoctorRepository;

public class ManageDoctorService {

    private ManageDoctorRepository doctorRepository;
    private Sql2o sql2o;

    public ManageDoctorService() {
        this.sql2o = Database.getSql2o();
        this.doctorRepository = new ManageDoctorRepository(this.sql2o);
    }

    public List<ManageDoctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public void addDoctor(String name, String speciality) {
        if (name == null || name.isEmpty()) return;
        ManageDoctor newDoctor = new ManageDoctor(name, speciality);

        doctorRepository.save(newDoctor);
    }

    public void deleteDoctor(String id) {
        doctorRepository.deleteById(id);
    }

    public ManageDoctor login(String idStr, String name) {
        try {
            if (idStr == null || name == null) return null;
            return doctorRepository.login(Integer.parseInt(idStr.trim()), name.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}