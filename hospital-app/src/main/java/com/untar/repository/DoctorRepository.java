package com.untar.repository;

import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import com.untar.models.Doctor;

public class DoctorRepository {

    private Sql2o sql2o;

    public DoctorRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public List<Doctor> findAll() {
        String sql = "SELECT * FROM doctors";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Doctor.class);
        }
    }

    public void save(Doctor doctor) {
        String sql = "INSERT INTO doctors (name, speciality) VALUES (:name, :speciality)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                .bind(doctor)
                .executeUpdate();
        }
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM doctors WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                .addParameter("id", id)
                .executeUpdate();
        }
    }
}