package com.untar.controllers;

import static spark.Spark.*;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.List;

import com.untar.dao.PatientDAO;
import com.untar.models.Appointment;

public class PatientController {

    public static void init() {

        // Dashboard pasien
        get("/patient/dashboard", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            return render(model, "patient_dashboard.html");
        });

        // Form request konsultasi
        get("/patient/request", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("dokter", PatientDAO.getAllDoctors());
            return render(model, "patient_request_appointment.html");
        });

        // Submit request
        post("/patient/request/submit", (req, res) -> {
            int pasienId = Integer.parseInt(req.session().attribute("patient_id"));
            int dokterId = Integer.parseInt(req.queryParams("doctor_id"));
            String date = req.queryParams("date");
            String time = req.queryParams("time");

            PatientDAO.saveAppointment(dokterId, pasienId, date, time);

            res.redirect("/patient/history");
            return null;
        });

        // History pasien
        get("/patient/history", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            int pasienId = Integer.parseInt(req.session().attribute("patient_id"));

            List<Appointment> history = PatientDAO.getHistoryByPatientId(pasienId);

            model.put("appointments", history);
            return render(model, "patient_history.html");
        });
    }

    private static String render(HashMap<String, Object> model, String templatePath) {
        return new HandlebarsTemplateEngine().render(
                new ModelAndView(model, templatePath)
        );
    }
}