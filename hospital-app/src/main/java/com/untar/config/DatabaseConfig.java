package com.untar.config;

import org.sql2o.Sql2o;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {

    private static Sql2o sql2o;

    public static Sql2o getSql2o() {
        if (sql2o == null) {
            try (InputStream input = DatabaseConfig.class
                    .getClassLoader()
                    .getResourceAsStream("application.properties")) {

                Properties props = new Properties();
                props.load(input);

                sql2o = new Sql2o(
                        props.getProperty("db.url"),
                        props.getProperty("db.user"),
                        props.getProperty("db.password")
                );

            } catch (Exception e) {
                throw new RuntimeException("⚠ Gagal load konfigurasi database: " + e.getMessage());
            }
        }
        return sql2o;
    }
}
