package com.untar.controllers;

import static spark.Spark.*;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.untar.repository.AppointmentScheduleRepository;
import com.untar.repository.DoctorRepository;
import com.untar.repository.PatientRepository;
import com.untar.services.ConsultationService;
import com.untar.services.ManageDoctorService;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

public class AdminController {

    private ManageDoctorService doctorService = new ManageDoctorService();
    private ConsultationService consultationService = new ConsultationService();
    private VelocityTemplateEngine engine = new VelocityTemplateEngine();

    public static void init() {
        get("/admin/dashboard", (req, res) -> {
            return renderHTML("/templates/admin/dashboard.html");
        });

        get("/admin/doctors-list", (req, res) -> {
            var doctors = DoctorRepository.getAll();

            StringBuilder rows = new StringBuilder();
            for (var d : doctors) {
                rows.append("<tr>")
                        .append("<td>").append(d.getNama()).append("</td>")
                        .append("<td>").append(d.getSpesialis()).append("</td>")
                        .append("<td>").append(d.getNomor_hp()).append("</td>")
                    .append("</tr>");
            }

            return renderHTML("/templates/admin/doctors-list.html")
                    .replace("{{doctorsRows}}", rows.toString());
        });

        get("/admin/patients-list", (req, res) -> {
            var patients = PatientRepository.getAll();

            StringBuilder rows = new StringBuilder();
            for (var p : patients) {
                rows.append("<tr>")
                        .append("<td>").append(p.getNama()).append("</td>")
                        .append("<td>").append(p.getEmail()).append("</td>")
                        .append("<td>").append(p.getNomor_hp()).append("</td>")
                        .append("<td>").append(p.getPassword()).append("</td>")
                    .append("</tr>");
            }

            return renderHTML("/templates/admin/patients-list.html")
                    .replace("{{patientsRows}}", rows.toString());
        });

        get("/admin/appointment-doctors", (req, res) -> {
            var schedules = AppointmentScheduleRepository.getAll();
            var doctors = DoctorRepository.getAll();

            StringBuilder options = new StringBuilder();
            for (var d : doctors) {
                options.append("<option value='")
                    .append(d.getId())
                    .append("'>Dr. ")
                    .append(d.getNama())
                    .append(" (")
                    .append(d.getSpesialis())
                    .append(")</option>");
            }

            StringBuilder rows = new StringBuilder();
            for (var s : schedules) {
                rows.append("<tr>")
                    .append("<td>").append(s.getNama_dokter()).append("</td>")
                    .append("<td>").append(s.getTanggal()).append("</td>")
                    .append("<td>").append(s.getWaktu_mulai()).append("</td>")
                    .append("<td>").append(s.getWaktu_selesai()).append("</td>")
                    .append("<td>").append(s.getSlot_total()).append("</td>")
                    .append("<td>")
                        .append("<form action='/admin/schedule/delete' method='post' onsubmit='return confirm(\"Yakin hapus?\")'>")
                            .append("<input type='hidden' name='id' value='").append(s.getId()).append("'>")
                            .append("<button type='submit'>Hapus</button>")
                        .append("</form>")
                    .append("</td>")
                .append("</tr>");
            }

            return renderHTML("/templates/admin/appointment-doctors.html")
                    .replace("{{doctorOptions}}", options.toString())
                    .replace("{{scheduleRows}}", rows.toString());
        });

        post("/admin/appointment-doctors/create", (req, res) -> {
            int doctorId = Integer.parseInt(req.queryParams("id_doctor"));
            String tanggal = req.queryParams("tanggal");
            String mulai = req.queryParams("mulai");
            String selesai = req.queryParams("selesai");
            int slot = Integer.parseInt(req.queryParams("slot"));

            AppointmentScheduleRepository.save(doctorId, tanggal, mulai, selesai, slot);

            res.redirect("/admin/appointment-doctors");
            return null;
        });

        post("/admin/schedule/delete", (req, res) -> {
            int id = Integer.parseInt(req.queryParams("id"));
            AppointmentScheduleRepository.delete(id);
            res.redirect("/admin/appointment-doctors");
            return null;
        });

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

    private static String renderHTML(String path) {
        try (InputStream input = AdminController.class.getResourceAsStream(path)) {
            if (input == null) {
                return "❌ Template tidak ditemukan: " + path;
            }
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "❌ Error load template: " + e.getMessage();
        }
    }
}