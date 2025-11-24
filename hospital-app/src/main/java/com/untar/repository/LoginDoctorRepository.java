package com.untar.repository;

import com.untar.config.DatabaseConfig;
import com.untar.models.LoginDoctor;
import org.sql2o.Connection;

public class LoginDoctorRepository {

    public static LoginDoctor login(String nama, int nomorHp) {
        String sql = "SELECT * FROM login_dokter WHERE nama = :nama AND nomor_hp = :hp";
        
        try (Connection con = DatabaseConfig.getSql2o().open()) {
            return con.createQuery(sql)
                    .addParameter("nama", nama)
                    .addParameter("hp", nomorHp)
                    .executeAndFetchFirst(LoginDoctor.class);
        } catch (Exception e) {
            System.out.println("❌ Error Doctor Repo: " + e.getMessage());
            return null;
        }
    }
}