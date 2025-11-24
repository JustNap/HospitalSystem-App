package com.untar.repository;

import org.sql2o.Connection;

import com.untar.config.Database;
import com.untar.models.User;

public class UserRepository {

    // REGISTER
    public static boolean register(User user) {
        String sql = "INSERT INTO users (full_name, email, password, role) VALUES (:fullName, :email, :password, :role)";
        try (Connection con = Database.getSql2o().open()) {
            con.createQuery(sql)
                .addParameter("fullName", user.getFullName())
                .addParameter("email", user.getEmail())
                .addParameter("password", user.getPassword())
                .addParameter("role", user.getRole())
                .executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("REGISTER ERROR: " + e.getMessage());
            return false;
        }
    }

    // LOGIN
    public static User login(String email, String password) {
        String sql = "SELECT * FROM users WHERE email=:email AND password=:password";

        try (Connection con = Database.getSql2o().open()) {
            return con.createQuery(sql)
                     .addParameter("email", email)
                     .addParameter("password", password)
                     .executeAndFetchFirst(User.class);
        } catch (Exception e) {
            System.out.println("LOGIN ERROR: " + e.getMessage());
            return null;
        }
    }
}
