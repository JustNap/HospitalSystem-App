package com.untar.config;

import org.sql2o.Sql2o;

public class Database {

    private static Sql2o sql2o;

    public static void init() {
        System.out.println("Menginisialisasi koneksi Database...");
        String url = "jdbc:mysql://localhost:3306/db_hospital";
        String user = "root";
        String password = "12345";

        sql2o = new Sql2o(url, user, password);
        System.out.println("Database berhasil dikonfigurasi!");
    }

    public static Sql2o getSql2o() {
        return sql2o;
    }
}
