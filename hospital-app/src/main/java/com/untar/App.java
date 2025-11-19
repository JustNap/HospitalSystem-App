package com.untar;

import static spark.Spark.*;

import com.untar.controllers.PatientController;

public class App {

    public static void main(String[] args) {

        port(4567);

        staticFiles.location("/public");

        PatientController.init();

        System.out.println("Server berjalan di http://localhost:4567/patient/dashboard");
    }
}