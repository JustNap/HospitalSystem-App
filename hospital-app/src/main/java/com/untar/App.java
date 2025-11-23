package com.untar;

import com.untar.controllers.AdminController;

import static spark.Spark.port;
import static spark.Spark.staticFiles;

public class App
{
    public static void main( String[] args )
    {
        port(4567);
        staticFiles.location("/public");
        AdminController.init();
        System.out.println("Server berjalan di http://localhost:4567/admin/dashboard");
    }
}