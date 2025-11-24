package com.untar.repository;

import com.untar.config.DatabaseConfig;
import com.untar.models.Consultation;
import org.sql2o.Connection;

import java.util.List;

public class ConsultationRepository {

    public static List<Consultation> getAll() {
        String sql = "SELECT id, patient_name, date, diagnosis, prescription, create_at FROM riwayat ORDER BY create_at DESC";

        try (Connection con = DatabaseConfig.getSql2o().open()) {
            return con.createQuery(sql).executeAndFetch(Consultation.class);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Gagal mengambil data Consultation (riwayat) dari DB: " + e.getMessage());
            return null;
        }
    }
}