package com.untar.repository;

import com.untar.config.DatabaseConfig;
import com.untar.models.Doctor;
import org.sql2o.Connection;
import java.util.List;

public class DoctorRepository {

    public static List<Doctor> getAll() {
        String sql = "SELECT id, nama, spesialis, nomor_hp FROM login_dokter";
        try (Connection con = DatabaseConfig.getSql2o().open()) {
            return con.createQuery(sql).executeAndFetch(Doctor.class);
        }
    }
}
