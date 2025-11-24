package com.untar;

import com.untar.controllers.AdminController;
import com.untar.controllers.DoctorController;
import com.untar.controllers.LoginPatientController;

import com.untar.controllers.LoginAdminController;
import com.untar.controllers.LoginDoctorController;
import com.untar.controllers.LoginPatientController;
// -----------------------------------------------

import static spark.Spark.port;
import static spark.Spark.staticFiles;

public class App
{
    public static void main( String[] args )
    {

        port(4567);
        

        staticFiles.location("/public");

        AdminController.init();
        DoctorController.registerRoutes(); 
        LoginPatientController.init();       


        LoginAdminController.init();
        LoginDoctorController.init();
        LoginPatientController.init();

        System.out.println("==================================================");
        System.out.println("✅ Server Berjalan!");
        System.out.println("   http://localhost:4567/patient/login");
        System.out.println("   http://localhost:4567/doctor/login");
        System.out.println("   http://localhost:4567/admin/login");
        System.out.println("==================================================");
    }
}