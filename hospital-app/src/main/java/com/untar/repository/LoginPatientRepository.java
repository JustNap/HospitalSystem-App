package com.untar.repository;

import com.untar.config.DatabaseConfig;
import com.untar.models.LoginPatient;
import org.sql2o.Connection;

public class LoginPatientRepository {

    public static void save(String nama, String email, String hp, String password) {
        String sql = "INSERT INTO login_pasien (nama, email, nomor_hp, password) " +
                     "VALUES (:nama, :email, :hp, :password)";
        try (Connection con = DatabaseConfig.getSql2o().open()) {
            con.createQuery(sql)
                .addParameter("nama", nama)
                .addParameter("email", email)
                .addParameter("hp", hp)
                .addParameter("password", password)
                .executeUpdate();
        }
    }

    public static LoginPatient findByEmail(String email) {
        String sql = "SELECT * FROM login_pasien WHERE email = :email LIMIT 1";
        try (Connection con = DatabaseConfig.getSql2o().open()) {
            return con.createQuery(sql)
                    .addParameter("email", email)
                    .executeAndFetchFirst(LoginPatient.class);
        }
    }
}
