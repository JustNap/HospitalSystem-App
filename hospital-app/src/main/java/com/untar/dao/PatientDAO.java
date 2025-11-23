package com.untar.dao;

import com.untar.models.Appointment;
import com.untar.models.AppointmentSchedule;
import com.untar.models.Doctor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class PatientDAO {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/db_hospital";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "samuelrg20";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
    }

    /* =============================
     * GET LIST DOKTER
     * ============================= */
    public static List<Doctor> getAllDoctors() {
        List<Doctor> list = new ArrayList<>();
        String sql = "SELECT id, nama, spesialis, nomor_hp FROM login_dokter";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Doctor(
                        rs.getInt("id"),
                        rs.getString("nama"),
                        rs.getString("spesialis"),
                        rs.getString("nomor_hp")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }

    /* =============================
     * GET SCHEDULE BY DOCTOR
     * ============================= */
    public static List<AppointmentSchedule> getScheduleByDoctorId(int doctorId) {
        List<AppointmentSchedule> list = new ArrayList<>();

        String sql = """
                SELECT a.*, d.nama AS nama_dokter
                FROM appointment_schedule a
                JOIN login_dokter d ON a.id_dokter = d.id
                WHERE a.id_dokter = ? AND a.slot_sisa > 0
                ORDER BY a.tanggal, a.waktu_mulai
                """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, doctorId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AppointmentSchedule s = new AppointmentSchedule();

                    s.setId(rs.getInt("id"));
                    s.setId_dokter(rs.getInt("id_dokter"));
                    s.setTanggal(rs.getDate("tanggal"));
                    s.setWaktu_mulai(rs.getTime("waktu_mulai"));
                    s.setWaktu_selesai(rs.getTime("waktu_selesai"));
                    s.setSlot_total(rs.getInt("slot_total"));
                    s.setSlot_sisa(rs.getInt("slot_sisa"));
                    s.setNama_dokter(rs.getString("nama_dokter"));

                    list.add(s);
                }
            }

        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }

    /* =============================
     * BOOK SCHEDULE
     * ============================= */
    public static boolean bookSchedule(int scheduleId, int patientId) {

        String sqlUpdateSlot = """
                UPDATE appointment_schedule 
                SET slot_sisa = slot_sisa - 1
                WHERE id = ? AND slot_sisa > 0
                """;

        String sqlInsert = """
                INSERT INTO appointment (id_jadwal, id_pasien)
                VALUES (?, ?)
                """;

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            // kurangi slot
            try (PreparedStatement ps = conn.prepareStatement(sqlUpdateSlot)) {
                ps.setInt(1, scheduleId);
                int updated = ps.executeUpdate();
                if (updated == 0) {
                    conn.rollback();
                    return false;
                }
            }

            // insert appointment
            try (PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
                ps.setInt(1, scheduleId);
                ps.setInt(2, patientId);
                ps.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (Exception e) { e.printStackTrace(); }

        return false;
    }

    /* =============================
     * HISTORY PASIEN
     * ============================= */
    public static List<Appointment> getHistoryByPatientId(int pasienId) {
        List<Appointment> list = new ArrayList<>();

        String sql = """
                SELECT a.id, d.nama AS dokter, d.spesialis,
                       s.tanggal, s.waktu_mulai AS waktu
                FROM appointment a
                JOIN appointment_schedule s ON a.id_jadwal = s.id
                JOIN login_dokter d ON s.id_dokter = d.id
                WHERE a.id_pasien = ?
                ORDER BY s.tanggal DESC, s.waktu_mulai DESC
                """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, pasienId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Appointment(
                            rs.getInt("id"),
                            rs.getString("dokter"),
                            rs.getString("spesialis"),
                            rs.getString("tanggal"),
                            rs.getString("waktu")
                    ));
                }
            }

        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }

    public static List<Map<String, Object>> getDiagnosisByPatientId(int id) {
        List<Map<String, Object>> list = new ArrayList<>();

        String sql = """
        SELECT patient_name, examination_date, diagnosis, prescription, created_at
        FROM doctor_diagnosis
        WHERE patient_name = (
            SELECT nama FROM login_pasien WHERE id = ?
        )
        ORDER BY created_at DESC
    """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("patient_name", rs.getString("patient_name"));
                row.put("examination_date", rs.getDate("examination_date"));
                row.put("diagnosis", rs.getString("diagnosis"));
                row.put("prescription", rs.getString("prescription"));
                row.put("created_at", rs.getTimestamp("created_at"));
                list.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
