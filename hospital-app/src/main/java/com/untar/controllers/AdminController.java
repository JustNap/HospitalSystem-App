package com.untar.controllers;

import static spark.Spark.*;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class AdminController {

    public static void init() {
        get("/admin/dashboard", (req, res) -> {
            return renderHTML("/templates/admin/dashboard.html");
        });

        get("/admin/doctors-list", (req, res) -> {
            return renderHTML("/templates/admin/doctors-list.html");
        });

        get("/admin/patients-list", (req, res) -> {
            return renderHTML("/templates/admin/patients-list.html");
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
