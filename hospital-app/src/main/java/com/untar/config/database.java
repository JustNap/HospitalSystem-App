package com.untar.config;

import org.sql2o.Sql2o;

public class Database {
    public static Sql2o sql2o;

    static {
        sql2o = new Sql2o(
            "jdbc:mysql://localhost:3306/db_hospital",  // ganti sesuai nama DB
            "root",     // user MySQL kamu
            ""          // password MySQL
        );
    }
}
