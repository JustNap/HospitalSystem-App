package com.untar.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.untar.models.User;

public class UserDAO {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/db_hospital";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "12345";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
    }

    public boolean register(User user) {
        String sql = "INSERT INTO users(full_name, email, password, role) VALUES(?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Gagal register: " + e.getMessage());
            return false;
        }
    }

    public User login(String email, String password) {
        String sql = "SELECT * FROM users WHERE email=? AND password=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("role")
                );
            }
        } catch (SQLException e) {
            System.err.println("Login error: " + e.getMessage());
        }
        return null;
    }
}
