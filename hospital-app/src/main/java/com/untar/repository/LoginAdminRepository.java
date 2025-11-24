package com.untar.repository;

import com.untar.config.DatabaseConfig;
import com.untar.models.LoginAdmin;
import org.sql2o.Connection;

public class LoginAdminRepository {

    public static LoginAdmin login(String nama, int nomorHp) {
        String sql = "SELECT * FROM login_admin WHERE nama = :nama AND nomor_hp = :hp";
        
        try (Connection con = DatabaseConfig.getSql2o().open()) {
            return con.createQuery(sql)
                    .addParameter("nama", nama)
                    .addParameter("hp", nomorHp)
                    .executeAndFetchFirst(LoginAdmin.class);
        } catch (Exception e) {
            System.out.println("❌ Error Admin Repo: " + e.getMessage());
            return null;
        }
    }
}