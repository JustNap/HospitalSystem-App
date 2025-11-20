package com.untar.controllers;

import com.untar.services.DokterService;
import com.untar.models.DoctorAppointment;
import com.untar.models.DiagnosisDoctor;
import spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import java.sql.Date;
import java.util.*;

public class DoctorController {

    private static String doctorName = "Dr. William";
    private static DokterService service = new DokterService();

    public static void registerRoutes() {
        
        // 1. MENU UTAMA
        Spark.get("/doctor/menu", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("doctorName", doctorName);
            return new ThymeleafTemplateEngine().render(new ModelAndView(model, "doctor_menu"));
        });

        // 2. JADWAL DOKTER
        Spark.get("/doctor/jadwal", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("doctorName", doctorName);
            model.put("appointments", service.getTodayAppointments());
            return new ThymeleafTemplateEngine().render(new ModelAndView(model, "doctor_page_jadwal"));
        });

        // 3a. FORM DIAGNOSA (DARI JADWAL)
        Spark.get("/doctor/diagnosa", (req, res) -> {
            String idStr = req.queryParams("id");
            DoctorAppointment apt;

            if (idStr != null) {
                int id = Integer.parseInt(idStr);
                apt = service.getAppointmentById(id);
                if (apt == null) return "Data Pasien Tidak Ditemukan.";
            } else {
                apt = new DoctorAppointment();
                apt.setId(-1); 
                apt.setPatientName("Belum Pilih Pasien");
            }

            Map<String, Object> model = new HashMap<>();
            model.put("doctorName", doctorName);
            model.put("appointment", apt);
            model.put("showSuccess", "true".equals(req.queryParams("success")));
            model.put("formAction", "/doctor/submit-diagnosis"); 
            
            if (apt.getId() == -1) {
                model.put("pageTitle", "Silakan Pilih Pasien dari Jadwal");
            } else {
                model.put("pageTitle", "Formulir Diagnosa: " + apt.getPatientName());
            }

            model.put("historyList", service.getListDiagnosis()); 
            return new ThymeleafTemplateEngine().render(new ModelAndView(model, "doctor_page_diagnosa"));
        });

        // 3b. FORM DIAGNOSA MANUAL (UNTUK LAPORAN)
        Spark.get("/doctor/diagnosa-manual", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("doctorName", doctorName);

            DoctorAppointment dummy = new DoctorAppointment();
            dummy.setId(0); 
            dummy.setPatientName(""); 
            model.put("appointment", dummy);
            
            model.put("showSuccess", "true".equals(req.queryParams("success")));
            model.put("formAction", "/doctor/submit-riwayat"); 
            model.put("pageTitle", "Input Laporan Baru (Table Riwayat)");
            model.put("historyList", service.getManualDiagnosis());

            // Render file HTML khusus manual (doctor_form_lapor.html)
            return new ThymeleafTemplateEngine().render(new ModelAndView(model, "doctor_form_lapor"));
        });

        // 4. SUBMIT DATA
        Spark.post("/doctor/submit-diagnosis", (req, res) -> {
            handleSave(req, "diagnosis");
            res.redirect("/doctor/diagnosa?success=true&id=" + req.queryParams("appointmentId"));
            return null;
        });

        Spark.post("/doctor/submit-riwayat", (req, res) -> {
            handleSave(req, "riwayat");
            res.redirect("/doctor/diagnosa-manual?success=true");
            return null;
        });

        // 5. HALAMAN LAPOR (Tabel Riwayat Utama)
        Spark.get("/doctor/lapor", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("doctorName", doctorName);
            model.put("diagnoses", service.getListRiwayat()); 
            return new ThymeleafTemplateEngine().render(new ModelAndView(model, "doctor_page_lapor"));
        });
        
        // ========================================================================
        // 6. ROUTE HAPUS 1: DARI TABEL RIWAYAT (Untuk Form Manual)
        // ========================================================================
        Spark.post("/doctor/delete-diagnosis", (req, res) -> {
             String idStr = req.queryParams("id");
             
             System.out.println("[DEBUG] Delete Riwayat ID: " + idStr); // Debugging

             if (idStr != null) {
                 service.deleteDiagnosis(Integer.parseInt(idStr));
             }
             
             // Redirect pintar: Tetap di halaman manual
             res.redirect("/doctor/diagnosa-manual");
             return null;
        });

        // ========================================================================
        // 7. ROUTE HAPUS 2: DARI TABEL DIAGNOSIS (Untuk Form Jadwal)
        // ========================================================================
        Spark.post("/doctor/delete-from-diagnosis", (req, res) -> {
             String idStr = req.queryParams("id");
             String aptId = req.queryParams("appointmentId");
             
             System.out.println("[DEBUG] Delete Diagnosis ID: " + idStr); // Debugging
             
             if (idStr != null) {
                 service.deleteFromDiagnosis(Integer.parseInt(idStr));
             }
             
             if ("-1".equals(aptId)) {
                 res.redirect("/doctor/diagnosa");
             } else {
                 res.redirect("/doctor/diagnosa?id=" + aptId);
             }
             return null;
        });

        // ========================================================================
        // 8. ROUTE HAPUS 3: KHUSUS HALAMAN UTAMA LAPOR (TAMBAHAN)
        // ========================================================================
        // Route ini khusus menangani tombol hapus di halaman doctor_page_lapor.html
        Spark.post("/doctor/delete-diagnosis-main", (req, res) -> {
             String idStr = req.queryParams("id");
             
             System.out.println("[DEBUG] Delete dari Menu Lapor ID: " + idStr); // Debugging
             
             if (idStr != null) {
                 // Hapus dari tabel riwayat
                 service.deleteDiagnosis(Integer.parseInt(idStr));
             }
             res.redirect("/doctor/lapor");
             return null;
        });
    }

    private static void handleSave(Request req, String targetTable) {
        try {
            int aptId = Integer.parseInt(req.queryParams("appointmentId"));
            String name = req.queryParams("patientName");
            String ageStr = req.queryParams("patientAge");
            int age = (ageStr != null && !ageStr.isEmpty()) ? Integer.parseInt(ageStr) : 0;
            String gender = req.queryParams("gender");
            Date date = Date.valueOf(req.queryParams("date"));
            String diag = req.queryParams("diagnosis");
            String pres = req.queryParams("prescription");

            DiagnosisDoctor d = new DiagnosisDoctor(aptId, name, age, gender, date, diag, pres);
            if (targetTable.equals("diagnosis")) service.saveToDiagnosis(d);
            else service.saveToRiwayat(d);
        } catch (Exception e) { e.printStackTrace(); }
    }
}