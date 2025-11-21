package com.untar;

import com.untar.controllers.AdminController;

import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.staticFiles;

public class App {
    public static void main(String[] args) {
        port(4567);
        staticFiles.externalLocation("src/main/resources/public");
        exception(Exception.class, (exception, request, response) -> {
            exception.printStackTrace();
            response.body("<h2>Terjadi Error (500)</h2>" +
                            "<h3>Penyebab:</h3>" +
                            "<pre>" + exception.getMessage() + "</pre>" +
                            "<h3>Detail Teknis:</h3>" +
                            "<pre>" + java.util.Arrays.toString(exception.getStackTrace()) + "</pre>");
        });
        get("/", (req, res) -> {
            res.redirect("/admin/dashboard");
            return null;
        });

        new AdminController().routes();

        System.out.println("Server berjalan di http://localhost:4567");
    }
}