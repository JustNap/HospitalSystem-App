package com.untar.repository;

import com.untar.config.DatabaseConfig;
import com.untar.models.DoctorAppointment;
import org.sql2o.Connection;
import java.util.List;

public class AppointmentRepository {

    public List<DoctorAppointment> getTodayAppointments() {

        String sql = """
            SELECT 
                id,
                patient_name,
                time,
                complaint,
                created_at
            FROM appointments
            WHERE DATE(created_at) = CURDATE()
        """;

        try (Connection con = DatabaseConfig.getSql2o().open()) {
            return con.createQuery(sql).executeAndFetch(DoctorAppointment.class);
        }catch (Exception e) {
            // CETAK ERROR SECARA DETAIL KE CONSOLE
            System.err.println("❌ SQL2O MAPPING FAILED in getTodayAppointments:");
            e.printStackTrace(); 
            return null;
        }
    }

    public DoctorAppointment getAppointmentById(int id) {

        String sql = """
            SELECT 
                id,
                patient_name ,
                time,
                complaint,
                created_at
            FROM appointments
            WHERE id = :id
        """;

        try (Connection con = DatabaseConfig.getSql2o().open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(DoctorAppointment.class);
        }
    }
}