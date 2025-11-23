// package com.untar.config;

// import org.sql2o.Sql2o;

// public class Database {
//     private static Sql2o sql2o;

//     static {
//         try {
//             System.out.println("Menginisialisasi koneksi Database...");
//             DatabaseConfig config = new DatabaseConfig();
            
//             sql2o = new Sql2o(config.getUrl(), config.getUser(), config.getPassword());
            
//             System.out.println("Database berhasil dikonfigurasi!");
//         } catch (Exception e) {
//             System.err.println("GAGAL KONEKSI KE DATABASE!");
//             e.printStackTrace();
//             throw new RuntimeException("Error initializing database", e);
//         }
//     }

//     public static Sql2o getSql2o() {
//         return sql2o;
//     }
// }