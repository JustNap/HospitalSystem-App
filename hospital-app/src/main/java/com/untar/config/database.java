package com.untar.config;

import org.sql2o.Sql2o;

public class Database {
    private static Sql2o sql2o;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/db_hospital"; 
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "database123"; //Password ricky

    private Database() {}

    public static Sql2o getSql2o() {
        if (sql2o == null) {
            sql2o = new Sql2o(DB_URL, DB_USER, DB_PASSWORD);
        }
        return sql2o;
    }
}