package com.untar;

import static spark.Spark.*;
import com.untar.controllers.AdminController;

public class App 
{
    public static void main( String[] args )
    {
        port(4567);

        staticFiles.location("/public");

        AdminController.init();
        new AdminController().routes();

        System.out.println("Server berjalan di http://localhost:4567/admin/dashboard");
    }
}