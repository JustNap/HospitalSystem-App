package com.untar;

import com.untar.controllers.logincontroller;

import static spark.Spark.port;

public class App {
    public static void main(String[] args) {
        port(4567); // bebas jika mau pakai port lain

        logincontroller.init();
    }
}
