package com.untar.repository;

import com.untar.config.Database;
import com.untar.models.DoctorAppointment;
import org.sql2o.Connection;
import java.util.List;

public class AppointmentRepository {

    public List<DoctorAppointment> getTodayAppointments() {

        String sql = """
            SELECT 
                id,
                patient_name AS patientName,
                time,
                complaint,
                created_at AS createdAt
            FROM appointments
            WHERE DATE(created_at) = CURDATE()
        """;

        try (Connection con = Database.getSql2o().open()) {
            return con.createQuery(sql).executeAndFetch(DoctorAppointment.class);
        }
    }

    public DoctorAppointment getAppointmentById(int id) {

        String sql = """
            SELECT 
                id,
                patient_name AS patientName,
                time,
                complaint,
                created_at AS createdAt
            FROM appointments
            WHERE id = :id
        """;

        try (Connection con = Database.getSql2o().open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(DoctorAppointment.class);
        }
    }
}
