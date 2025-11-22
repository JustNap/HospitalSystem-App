package com.untar;

import com.untar.controllers.DoctorController;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) {

        port(4567);
        staticFiles.location("/public");

        exception(Exception.class, (exception, request, response) -> {
            exception.printStackTrace();
            response.body("ERROR 500: " + exception.getMessage());
        });

        DoctorController.registerRoutes();

        System.out.println("SERVER BERJALAN -> http://localhost:4567/doctor/menu");
    }
}
