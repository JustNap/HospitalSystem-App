package com.untar.controllers;

import static spark.Spark.*;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import com.untar.dao.PatientDAO;
import com.untar.models.Appointment;
import com.untar.models.AppointmentSchedule;
import com.untar.models.LoginPatient;

import java.util.HashMap;
import java.util.List;

public class PatientController {

    public static void init() {

        // ========== MIDDLEWARE: CEK LOGIN PASIEN ==========
        before("/patient/*", (req, res) -> {
            // kecuali login & register
            if (req.pathInfo().contains("/login") || req.pathInfo().contains("/register")) return;

            LoginPatient loggedIn = req.session().attribute("patient");
            if (loggedIn == null) {
                res.redirect("/patient/login");
            }
        });

        // ========== DASHBOARD ==========
        get("/patient/dashboard", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();

            LoginPatient loggedIn = req.session().attribute("patient");
            model.put("patientName", loggedIn.getNama());

            return render(model, "patient_dashboard.html");
        });

        // ========== AJAX GET SCHEDULE BY DOCTOR ==========
        get("/patient/schedule/:id", (req, res) -> {
            int doctorId = Integer.parseInt(req.params(":id"));
            List<AppointmentSchedule> list = PatientDAO.getScheduleByDoctorId(doctorId);

            StringBuilder json = new StringBuilder("[");

            for (int i = 0; i < list.size(); i++) {
                AppointmentSchedule s = list.get(i);
                json.append("{")
                        .append("\"id\":").append(s.getId()).append(",")
                        .append("\"tanggal\":\"").append(s.getTanggal()).append("\",")
                        .append("\"waktu_mulai\":\"").append(s.getWaktu_mulai()).append("\",")
                        .append("\"waktu_selesai\":\"").append(s.getWaktu_selesai()).append("\",")
                        .append("\"slot_sisa\":").append(s.getSlot_sisa())
                        .append("}");

                if (i < list.size() - 1) json.append(",");
            }

            json.append("]");

            res.type("application/json");
            return json.toString();
        });

        // ========== REQUEST PAGE ==========
        get("/patient/request", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("dokter", PatientDAO.getAllDoctors());
            return render(model, "patient_request_appointment.html");
        });

        // ========== SUBMIT REQUEST ==========
        post("/patient/request/submit", (req, res) -> {
            LoginPatient loggedIn = req.session().attribute("patient");
            int pasienId = loggedIn.getId();

            int scheduleId = Integer.parseInt(req.queryParams("schedule_id"));
            boolean ok = PatientDAO.bookSchedule(scheduleId, pasienId);

            if (ok) res.redirect("/patient/history");
            else    res.redirect("/patient/request?error=1");

            return null;
        });

        // ========== HISTORY ==========
        get("/patient/history", (req, res) -> {
            LoginPatient loggedIn = req.session().attribute("patient");
            int pasienId = loggedIn.getId();

            HashMap<String, Object> model = new HashMap<>();
            model.put("appointments", PatientDAO.getHistoryByPatientId(pasienId));

            return render(model, "patient_history.html");
        });

        // ========== DIAGNOSIS ==========
        get("/patient/diagnosis", (req, res) -> {
            LoginPatient loggedIn = req.session().attribute("patient");
            int pasienId = loggedIn.getId();

            HashMap<String, Object> model = new HashMap<>();
            model.put("diagnosis", PatientDAO.getDiagnosisByPatientId(pasienId));

            return render(model, "patient_diagnosis.html");
        });
    }

    // ========== TEMPLATE RENDERER ==========
    private static String render(HashMap<String, Object> model, String templatePath) {
        return new HandlebarsTemplateEngine().render(
                new ModelAndView(model, templatePath)
        );
    }
}
