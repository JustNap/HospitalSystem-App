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
        
        Spark.get("/doctor/menu", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("doctorName", doctorName);
            return new ThymeleafTemplateEngine().render(new ModelAndView(model, "doctor_menu"));
        });

        Spark.get("/doctor/jadwal", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("doctorName", doctorName);
            model.put("appointments", service.getTodayAppointments());
            return new ThymeleafTemplateEngine().render(new ModelAndView(model, "doctor_page_jadwal"));
        });

Spark.get("/doctor/diagnosa", (req, res) -> {
    try {
        // --- LOGGING ---
        System.out.println("1. Masuk Route Diagnosa");
        
        String idStr = req.queryParams("id");
        DoctorAppointment apt;

        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            System.out.println("2. Mencari ID: " + id);
            
            apt = service.getAppointmentById(id); // <--- Rawan Error
            
            System.out.println("3. Hasil DB: " + (apt == null ? "NULL" : apt.getPatient_name()));
            
            if (apt == null) return "Data Pasien Tidak Ditemukan.";
        } else {
            apt = new DoctorAppointment();
            apt.setId(-1); 
            apt.setPatient_name("Belum Pilih Pasien");
        }

        Map<String, Object> model = new HashMap<>();
        model.put("doctorName", doctorName);
        model.put("appointment", apt);
        model.put("showSuccess", "true".equals(req.queryParams("success")));
        model.put("formAction", "/doctor/submit-diagnosis"); 
        
        if (apt.getId() == -1) {
            model.put("pageTitle", "Silakan Pilih Pasien dari Jadwal");
        } else {
            model.put("pageTitle", "Formulir Diagnosa: " + apt.getPatient_name());
        }

        System.out.println("4. Mengambil History List...");
        List<?> history = service.getListDiagnosis(); 
        model.put("historyList", history); 

        System.out.println("5. Rendering Template...");
        return new ThymeleafTemplateEngine().render(new ModelAndView(model, "doctor_page_diagnosa"));
        
    } catch (Exception e) {
        e.printStackTrace(); 
        return "Terjadi Error di Server: " + e.getMessage();
    }
});

        Spark.get("/doctor/diagnosa-manual", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("doctorName", doctorName);

            DoctorAppointment dummy = new DoctorAppointment();
            dummy.setId(0); 
            dummy.setPatient_name(""); 
            model.put("appointment", dummy);
            
            model.put("showSuccess", "true".equals(req.queryParams("success")));
            model.put("formAction", "/doctor/submit-riwayat"); 
            model.put("pageTitle", "Input Laporan Baru (Table Riwayat)");
            model.put("historyList", service.getManualDiagnosis());

            return new ThymeleafTemplateEngine().render(new ModelAndView(model, "doctor_form_lapor"));
        });

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

        Spark.get("/doctor/lapor", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("doctorName", doctorName);
            model.put("diagnoses", service.getListRiwayat()); 
            return new ThymeleafTemplateEngine().render(new ModelAndView(model, "doctor_page_lapor"));
        });
        
        Spark.post("/doctor/delete-diagnosis", (req, res) -> {
             String idStr = req.queryParams("id");
             
             System.out.println("[DEBUG] Delete Riwayat ID: " + idStr); // Debugging

             if (idStr != null) {
                 service.deleteDiagnosis(Integer.parseInt(idStr));
             }
             
             res.redirect("/doctor/diagnosa-manual");
             return null;
        });
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

        Spark.post("/doctor/delete-diagnosis-main", (req, res) -> {
             String idStr = req.queryParams("id");
             
             System.out.println("[DEBUG] Delete dari Menu Lapor ID: " + idStr); // Debugging
             
             if (idStr != null) {
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