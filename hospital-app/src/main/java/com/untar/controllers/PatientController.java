package com.untar.controllers;

import static spark.Spark.*;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import com.untar.dao.PatientDAO;
import com.untar.models.Appointment;
import com.untar.models.AppointmentSchedule;
import com.untar.models.Doctor;

import java.util.HashMap;
import java.util.List;

public class PatientController {

    public static void init() {

        get("/patient/dashboard", (req, res) -> {
            return render(new HashMap<>(), "patient_dashboard.html");
        });

        // AJAX: get schedule by doctor ID
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

        // tampil request page
        get("/patient/request", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("dokter", PatientDAO.getAllDoctors());
            return render(model, "patient_request_appointment.html");
        });

        // submit booking
        post("/patient/request/submit", (req, res) -> {
            int pasienId = 1; // session simulation
            int scheduleId = Integer.parseInt(req.queryParams("schedule_id"));

            boolean ok = PatientDAO.bookSchedule(scheduleId, pasienId);

            if (ok) res.redirect("/patient/history");
            else    res.redirect("/patient/request?error=1");

            return null;
        });

        // history page
        get("/patient/history", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("appointments", PatientDAO.getHistoryByPatientId(1));
            return render(model, "patient_history.html");
        });

        get("/patient/diagnosis", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();

            int pasienId = 1; // nanti diganti session
            model.put("diagnosis", PatientDAO.getDiagnosisByPatientId(pasienId));

            return render(model, "patient_diagnosis.html");
        });


    }

    private static String render(HashMap<String, Object> model, String templatePath) {
        return new HandlebarsTemplateEngine().render(
                new ModelAndView(model, templatePath)
        );
    }
}
