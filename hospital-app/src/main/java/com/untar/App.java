package com.untar;

import com.untar.controllers.AdminController;
import com.untar.controllers.DoctorController;
import com.untar.controllers.PatientController;
import com.untar.controllers.LoginAdminController;
import com.untar.controllers.LoginDoctorController;
import com.untar.controllers.LoginPatientController;
import com.untar.config.DatabaseConfig; 

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        
        port(4567);
        
        staticFiles.location("/public");

        exception(Exception.class, (exception, request, response) -> {
            exception.printStackTrace(); // Tulis error di Terminal VS Code
            response.status(500);
            response.body("<html><body>" +
                    "<h1>TERJADI ERROR 500 (Internal Server Error)</h1>" +
                    "<h3>Penyebab: " + exception.getMessage() + "</h3>" +
                    "<pre style='background:#eee; padding:20px; border:1px solid #999;'>" + 
                    exception.toString() + 
                    "</pre>" +
                    "<p>Cek Terminal VS Code untuk detail lengkap.</p>" +
                    "</body></html>");
        });
        get("/test-db", (req, res) -> {
            try (org.sql2o.Connection con = DatabaseConfig.getSql2o().open()) {
                return "<h1>✅ Koneksi Database BERHASIL!</h1>";
            } catch (Exception e) {
                return "<h1>❌ Koneksi Database GAGAL!</h1><br>" + e.getMessage();
            }
        });

        AdminController.init();
        DoctorController.registerRoutes();
        PatientController.init();
        
        LoginAdminController.init();
        LoginDoctorController.init();
        LoginPatientController.init();

        get("/", (req, res) -> {
            res.redirect("/patient/login");
            return null;
        });

        System.out.println("==================================================");
        System.out.println("✅ Server Berjalan!");
        System.out.println("👉 Cek Database: http://localhost:4567/test-db");
        System.out.println("👉 Login Pasien: http://localhost:4567/patient/login");
        System.out.println("👉 Login Admin: http://localhost:4567/admin/login");
        System.out.println("👉 Login Doctor: http://localhost:4567/doctor/login");
        System.out.println("==================================================");
    }
}