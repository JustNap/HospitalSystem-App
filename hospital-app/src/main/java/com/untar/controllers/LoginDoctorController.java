package com.untar.controllers;

import com.untar.models.LoginDoctor;
import com.untar.repository.LoginDoctorRepository;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import java.util.HashMap;
import java.util.Map;
import static spark.Spark.*;

public class LoginDoctorController {

    public static void init() {
        
        get("/doctor/login", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            if (req.queryParams("error") != null) model.put("loginError", true);
            return new ThymeleafTemplateEngine().render(new ModelAndView(model, "doctor/login_doctor"));
        });

        post("/doctor/login", (req, res) -> {
            try {
                String nama = req.queryParams("nama");
                int hp = Integer.parseInt(req.queryParams("nomor_hp"));
                
                LoginDoctor doctor = LoginDoctorRepository.login(nama, hp);

                if (doctor != null) {
                    req.session().attribute("doctor", doctor);
                    res.redirect("/doctor/menu"); // Redirect ke menu dokter
                } else {
                    res.redirect("/doctor/login?error=true");
                }
            } catch (Exception e) {
                res.redirect("/doctor/login?error=true");
            }
            return null;
        });

        get("/doctor/menu", (req, res) -> {
            LoginDoctor doctor = req.session().attribute("doctor");
            if (doctor == null) {
                res.redirect("/doctor/login");
                return null;
            }

            Map<String, Object> model = new HashMap<>();
            model.put("doctorName", doctor.getNama());
            
            return new ThymeleafTemplateEngine().render(new ModelAndView(model, "doctor/doctor_menu"));
        });

        // 4. LOGOUT
        get("/doctor/logout", (req, res) -> {
            req.session().removeAttribute("doctor");
            res.redirect("/doctor/login");
            return null;
        });
    }
}