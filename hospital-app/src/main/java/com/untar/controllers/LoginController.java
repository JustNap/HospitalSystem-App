package com.untar.controllers;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.untar.models.User;
import com.untar.repository.UserRepository;

import static spark.Spark.get;
import static spark.Spark.post;

public class LoginController {

    public static void init() {

        // SHOW LOGIN PAGE
        get("/login", (req, res) -> render("login.html"));

        // LOGIN POST
        post("/login", (req, res) -> {
            String email = req.queryParams("email");
            String password = req.queryParams("password");

            User user = UserRepository.login(email, password);

            if (user == null) {
                return "<h3>Login gagal! Email atau password salah.</h3>";
            }

            res.redirect("/home");
            return null;
        });

        // SHOW REGISTER PAGE
        get("/register", (req, res) -> render("register.html"));

        // REGISTER POST
        post("/register", (req, res) -> {
            String fullName = req.queryParams("fullname");
            String email = req.queryParams("email");
            String password = req.queryParams("password");
            String role = req.queryParams("role");

            User user = new User(fullName, email, password, role);

            boolean ok = UserRepository.register(user);

            if (!ok) {
                return "<h3>Email sudah digunakan!</h3>";
            }

            res.redirect("/login");
            return null;
        });

        get("/home", (req, res) -> "Berhasil Login!");
    }

    // Render HTML
    private static String render(String file) {
        try {
            return Files.readString(Paths.get("src/main/resources/templates/" + file));
        } catch (Exception e) {
            return "Error load page: " + file;
        }
    }
}
