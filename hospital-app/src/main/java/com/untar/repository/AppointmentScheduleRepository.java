package com.untar.repository;

import com.untar.config.DatabaseConfig;
import com.untar.models.AppointmentSchedule;
import org.sql2o.Connection;
import java.util.List;

public class AppointmentScheduleRepository {

    public static List<AppointmentSchedule> getAll() {
     String sql = "SELECT a.*, d.nama AS nama_dokter " +
                "FROM appointment_schedule a " +
                "JOIN login_dokter d ON a.id_dokter = d.id";

    try (Connection con = DatabaseConfig.getSql2o().open()) {
        return con.createQuery(sql).executeAndFetch(AppointmentSchedule.class);
    }
    }

    public static void save(int doctorId, String tanggal, String start, String end, int slot) {
        String sql = "INSERT INTO appointment_schedule (id_dokter, tanggal, waktu_mulai, waktu_selesai, slot_total, slot_sisa) " +
                    "VALUES (:id_dokter, :tanggal, :mulai, :selesai, :slot_total, :slot_total)";
        try (Connection con = DatabaseConfig.getSql2o().open()) {
            con.createQuery(sql)
                .addParameter("id_dokter", doctorId)
                .addParameter("tanggal", tanggal)
                .addParameter("mulai", start)
                .addParameter("selesai", end)
                .addParameter("slot_total", slot)
                .executeUpdate();
        }
    }

    public static void delete(int id) {
        String sql = "DELETE FROM appointment_schedule WHERE id = :id";
        try (Connection con = DatabaseConfig.getSql2o().open()) {
            con.createQuery(sql).addParameter("id", id).executeUpdate();
        }
    }
}

