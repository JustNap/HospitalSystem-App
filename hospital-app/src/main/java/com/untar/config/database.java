package com.untar.config;

import org.sql2o.Sql2o;
import java.io.InputStream;
import java.util.Properties;

public class Database {

    private static Sql2o sql2o;

    private Database() {}

    public static Sql2o getSql2o() {
        if (sql2o == null) {
            try {
                Properties prop = new Properties();
                InputStream input = Database.class.getClassLoader().getResourceAsStream("application.properties");

                if (input == null) {
                    System.out.println("Maaf, tidak bisa menemukan file application.properties");
                    return null;
                }

                prop.load(input);

                String dbUrl = prop.getProperty("db.url");
                String dbUser = prop.getProperty("db.username");
                String dbPass = prop.getProperty("db.password");

                sql2o = new Sql2o(dbUrl, dbUser, dbPass);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error saat membaca konfigurasi database: " + e.getMessage());
            }
        }
        return sql2o;
    }
}