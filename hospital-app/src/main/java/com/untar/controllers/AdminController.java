package com.untar.controllers;

import static spark.Spark.*;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import com.untar.repository.DoctorRepository;
import com.untar.repository.PatientRepository;

public class AdminController {

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
            return renderHTML("/templates/admin/appointment-doctors.html");
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
