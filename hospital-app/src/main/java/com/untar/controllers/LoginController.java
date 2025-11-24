package com.untar.controllers;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.untar.dao.UserDAO;
import com.untar.models.User;

import static spark.Spark.get;
import static spark.Spark.post;

public class LoginController {

    private static UserDAO userDAO = new UserDAO();

    public static void init() {

        get("/login", (req, res) -> render("login.html"));
        get("/register", (req, res) -> render("register.html"));

        post("/register", (req, res) -> {
            String fullName = req.queryParams("fullName");
            String email = req.queryParams("email");
            String password = req.queryParams("password");
            String role = req.queryParams("role");

            User user = new User(fullName, email, password, role);
            boolean ok = userDAO.register(user);


            res.redirect("/login");
            return null;
        });

        post("/login", (req, res) -> {
            String email = req.queryParams("email");
            String password = req.queryParams("password");

            User user = userDAO.login(email, password);
            if (user == null) {
                return "<h3>Login gagal! Email atau password salah.</h3>";
            }

            res.redirect("/home");
            return null;
        });

        get("/home", (req, res) -> "Berhasil Login!");
    }

    private static String render(String file) {
        try {
            return Files.readString(Paths.get("src/main/resources/public/templates/" + file));
        } catch (Exception e) {
            return "Error load page: " + file;
        }
    }
}
