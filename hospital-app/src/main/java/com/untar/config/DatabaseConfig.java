package com.untar.config;

public class DatabaseConfig {
    // Sesuaikan dengan password MySQL kamu
    private String url = "jdbc:mysql://localhost:3306/db_hospital?serverTimezone=UTC";
    private String user = "root";
    private String password = "Ignatius@123"; 

    // Constructor Kosong
    public DatabaseConfig() {
        try {
            // Register Driver MySQL agar terbaca oleh Sql2o
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getUrl() { return url; }
    public String getUser() { return user; }
    public String getPassword() { return password; }
}