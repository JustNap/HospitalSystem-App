package com.untar.controllers;

import java.util.HashMap;
import java.util.Map;

import com.untar.services.ConsultationService;
import com.untar.services.DoctorService;

import spark.ModelAndView;
import static spark.Spark.get;
import static spark.Spark.post;
import spark.template.velocity.VelocityTemplateEngine;

public class AdminController {

    private DoctorService doctorService = new DoctorService();
    private ConsultationService consultationService = new ConsultationService();
    private VelocityTemplateEngine engine = new VelocityTemplateEngine();

    public void routes() {
        get("/admin/dashboard", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("title", "Admin Dashboard");
            model.put("active_page", "dashboard");
            model.put("totalDoctors", doctorService.getAllDoctors().size());
            model.put("totalConsultations", consultationService.getAllConsultations().size());

            String content = engine.render(
                new ModelAndView(model, "templates/admin-dashboard-content.html")
            );

            Map<String, Object> layoutModel = new HashMap<>();
            layoutModel.put("title", model.get("title"));
            layoutModel.put("active_page", model.get("active_page"));
            layoutModel.put("content", content);

            return engine.render(new ModelAndView(layoutModel, "templates/admin-layout.html"));
        });

        get("/admin/doctors", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("title", "Manajemen Dokter");
            model.put("active_page", "doctors");
            model.put("doctors", doctorService.getAllDoctors());

            String content = engine.render(
                new ModelAndView(model, "templates/admin-doctors.html")
            );

            Map<String, Object> layoutModel = new HashMap<>();
            layoutModel.put("title", model.get("title"));
            layoutModel.put("active_page", model.get("active_page"));
            layoutModel.put("content", content);

            return engine.render(new ModelAndView(layoutModel, "templates/admin-layout.html"));
        });

        get("/admin/consultations", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("title", "Laporan Konsultasi");
            model.put("active_page", "consultations");
            model.put("consultations", consultationService.getAllConsultations());

            String content = engine.render(
                new ModelAndView(model, "templates/admin-consultations.html")
            );

            Map<String, Object> layoutModel = new HashMap<>();
            layoutModel.put("title", model.get("title"));
            layoutModel.put("active_page", model.get("active_page"));
            layoutModel.put("content", content);

            return engine.render(new ModelAndView(layoutModel, "templates/admin-layout.html"));
        });

        get("/admin/patients", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("title", "Daftar Pasien");
            model.put("active_page", "patients");

            String content = engine.render(
                new ModelAndView(model, "templates/empty-content.html")
            );

            Map<String, Object> layoutModel = new HashMap<>();
            layoutModel.put("title", model.get("title"));
            layoutModel.put("active_page", model.get("active_page"));
            layoutModel.put("content", content);

            return engine.render(new ModelAndView(layoutModel, "templates/admin-layout.html"));
        });

        get("/admin/appointments", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("title", "Kelola Appointment");
            model.put("active_page", "appointments");

            String content = engine.render(
                new ModelAndView(model, "templates/empty-content.html")
            );

            Map<String, Object> layoutModel = new HashMap<>();
            layoutModel.put("title", model.get("title"));
            layoutModel.put("active_page", model.get("active_page"));
            layoutModel.put("content", content);

            return engine.render(new ModelAndView(layoutModel, "templates/admin-layout.html"));
        });

        post("/admin/doctors/add", (req, res) -> {
            String name = req.queryParams("doctorName");
            String speciality = req.queryParams("doctorSpec");

            doctorService.addDoctor(name, speciality);

            res.redirect("/admin/doctors");
            return null;
        });

        post("/admin/doctors/delete/:id", (req, res) -> {
            String id = req.params(":id");
            doctorService.deleteDoctor(id);
            res.redirect("/admin/doctors");
            return null;
        });
    }
}