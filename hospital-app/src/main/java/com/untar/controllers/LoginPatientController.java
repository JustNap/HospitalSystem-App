package com.untar.controllers;

import static spark.Spark.*;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.io.InputStream;

import com.untar.repository.LoginPatientRepository;
import com.untar.models.LoginPatient;

public class LoginPatientController {

    public static void init() {

        get("/patient/register", (req, res) -> {
            return renderHTML("/templates/patient/register-patient.html");
        });

        post("/patient/register", (req, res) -> {

            String nama = req.queryParams("nama");
            String email = req.queryParams("email");
            String hp = req.queryParams("nomor_hp");
            String pw = req.queryParams("password");
            String confirm = req.queryParams("confirm_password");

            if (!pw.equals(confirm)) {
                return "❌ Password tidak sama!";
            }

            LoginPatient existing = LoginPatientRepository.findByEmail(email);
            if (existing != null) {
                return "❌ Email sudah terdaftar!";
            }

            LoginPatientRepository.save(nama, email, hp, pw);

            res.redirect("/patient/login");
            return null;
        });

        get("/patient/login", (req, res) -> {
            return renderHTML("/templates/patient/login-patient.html");
        });

        post("/patient/login", (req, res) -> {

            String email = req.queryParams("email");
            String password = req.queryParams("password");

            LoginPatient p = LoginPatientRepository.findByEmail(email);

            if (p == null || !p.getPassword().equals(password)) {
                return "❌ Email atau password salah!";
            }

            req.session().attribute("patient_id", p.getId());
            req.session().attribute("patient_name", p.getNama());

            res.redirect("/patient/home");
            return null;
        });

        get("/patient/home", (req, res) -> {
            Integer id = req.session().attribute("patient_id");

            if (id == null) {
                res.redirect("/patient/login");
                return null;
            }

            return "✔ Welcome, " + req.session().attribute("patient_name");
        });
    }

    private static String renderHTML(String path) throws IOException {
        try (InputStream input = LoginPatientController.class.getResourceAsStream(path)) {
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

}
