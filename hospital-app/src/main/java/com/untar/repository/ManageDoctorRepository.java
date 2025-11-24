package com.untar.repository;

import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import com.untar.models.ManageDoctor;

public class ManageDoctorRepository {

    private Sql2o sql2o;

    public ManageDoctorRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public List<ManageDoctor> findAll() {
        String sql = "SELECT id, " +
                     "nama AS name, " +
                     "spesialis AS speciality, " +
                     "nomor_hp AS phoneNumber " +
                     "FROM login_dokter";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(ManageDoctor.class);
        }
    }

    public void save(ManageDoctor doctor) {
        String sql = "INSERT INTO login_dokter (nama, spesialis, nomor_hp) " +
                    "VALUES (:name, :speciality, :phoneNumber)";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                .bind(doctor)
                .executeUpdate();
        }
    }

    public void deleteById(String id) {
        String sql = "DELETE FROM login_dokter WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                .addParameter("id", id)
                .executeUpdate();
        }
    }

    public ManageDoctor login(int id, String name) {
        String sql = "SELECT id, nama AS name, spesialis AS speciality, " +
                    "nomor_hp AS phoneNumber " +
                    "FROM login_dokter WHERE id = :id AND nama = :name";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                .addParameter("id", id)
                .addParameter("name", name)
                .executeAndFetchFirst(ManageDoctor.class);
        }
    }
}