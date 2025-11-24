package com.untar;

import com.untar.controllers.LoginController;

public class App {
    public static void main(String[] args) {
        System.out.println("Starting server on port 4567...");
        LoginController.init();
    }
}
