package com.untar.repository;

import com.untar.config.DatabaseConfig;
import com.untar.models.Patient;
import org.sql2o.Connection;

import java.util.List;

public class PatientRepository {

    public static List<Patient> getAll() {
        String sql = "SELECT id, nama, email, nomor_hp, password FROM login_pasien";
        try (Connection con = DatabaseConfig.getSql2o().open()) {
            return con.createQuery(sql).executeAndFetch(Patient.class);
        }
    }
}

