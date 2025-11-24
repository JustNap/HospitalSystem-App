package com.untar.controllers;

import static spark.Spark.*;
import com.untar.repository.LoginPatientRepository;
import com.untar.models.LoginPatient;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import java.util.HashMap;
import java.util.Map;

public class LoginPatientController {

    public static void init() {
    
        get("/patient/register", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return render(model, "patient/register-patient"); 
        });

        post("/patient/register", (req, res) -> {
            String nama = req.queryParams("nama");
            String email = req.queryParams("email");
            String hp = req.queryParams("nomor_hp");
            String pw = req.queryParams("password");
            String confirm = req.queryParams("confirm_password");

            if (!pw.equals(confirm)) return "❌ Password tidak sama!";
            
            LoginPatient existing = LoginPatientRepository.findByEmail(email);
            if (existing != null) return "❌ Email sudah terdaftar!";

            LoginPatientRepository.save(nama, email, hp, pw);
            res.redirect("/patient/login");
            return null;
        });

        get("/patient/login", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            if (req.queryParams("error") != null) model.put("loginError", true);
            return render(model, "patient/login-patient");
        });

        post("/patient/login", (req, res) -> {
            String email = req.queryParams("email");
            String password = req.queryParams("password");

            LoginPatient p = LoginPatientRepository.findByEmail(email);

            if (p == null || !p.getPassword().equals(password)) {
                res.redirect("/patient/login?error=true");
                return null;
            }

            req.session().attribute("patient", p); 

            res.redirect("/patient/dashboard"); 
            return null;
        });

        get("/patient/logout", (req, res) -> {
            req.session().removeAttribute("patient");
            res.redirect("/patient/login");
            return null;
        });
    }

    private static String render(Map<String, Object> model, String templatePath) {
        return new ThymeleafTemplateEngine().render(new ModelAndView(model, templatePath));
    }
}