package com.untar.controllers;

import com.untar.models.LoginAdmin;
import com.untar.repository.LoginAdminRepository;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import java.util.HashMap;
import java.util.Map;
import static spark.Spark.*;

public class LoginAdminController {

    public static void init() {
        
        get("/admin/login", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            if (req.queryParams("error") != null) model.put("loginError", true);
            return new ThymeleafTemplateEngine().render(new ModelAndView(model, "admin/login_admin"));
        });

        post("/admin/login", (req, res) -> {
            try {
                String nama = req.queryParams("nama");
                int hp = Integer.parseInt(req.queryParams("nomor_hp"));
                
                LoginAdmin admin = LoginAdminRepository.login(nama, hp);

                if (admin != null) {
                    req.session().attribute("admin", admin);
                    res.redirect("/admin/dashboard"); 
                } else {
                    res.redirect("/admin/login?error=true");
                }
            } catch (Exception e) {
                res.redirect("/admin/login?error=true");
            }
            return null;
        });

        get("/admin/dashboard", (req, res) -> {
            // Cek apakah user sudah login?
            LoginAdmin admin = req.session().attribute("admin");
            if (admin == null) {
                res.redirect("/admin/login"); 
                return null;
            }

            Map<String, Object> model = new HashMap<>();
            model.put("adminName", admin.getNama());
            
            return new ThymeleafTemplateEngine().render(new ModelAndView(model, "admin/dashboard"));
        });

        get("/admin/logout", (req, res) -> {
            req.session().removeAttribute("admin");
            res.redirect("/admin/login");
            return null;
        });
    }
}