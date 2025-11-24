package com.untar.dao;

import com.untar.config.DatabaseConfig;
import com.untar.models.Appointment;
import com.untar.models.AppointmentSchedule;
import com.untar.models.DiagnosisDoctor;
import com.untar.models.Doctor;
import org.sql2o.Connection;

import java.util.List;
import java.util.Map;

public class PatientDAO {

    /* =============================
     * GET LIST DOKTER
     * ============================= */
    public static List<Doctor> getAllDoctors() {
        String sql = "SELECT id, nama, spesialis, nomor_hp FROM login_dokter";

        try (Connection con = DatabaseConfig.getSql2o().open()) {
            // executeAndFetch otomatis memetakan kolom DB ke field Java (Doctor)
            return con.createQuery(sql).executeAndFetch(Doctor.class);
        } catch (Exception e) {
        // Tambahkan ini agar Error muncul di Console IntelliJ
        e.printStackTrace(); 
        return null;
        }
    }

    /* =============================
     * GET SCHEDULE BY DOCTOR
     * ============================= */
    public static List<AppointmentSchedule> getScheduleByDoctorId(int doctorId) {
        String sql = """
                SELECT a.*, d.nama AS nama_dokter
                FROM appointment_schedule a
                JOIN login_dokter d ON a.id_dokter = d.id
                WHERE a.id_dokter = :doctorId AND a.slot_sisa > 0
                ORDER BY a.tanggal, a.waktu_mulai
                """;

        try (Connection con = DatabaseConfig.getSql2o().open()) {
            return con.createQuery(sql)
                    .addParameter("doctorId", doctorId) // Menggunakan named parameter (:doctorId)
                    .executeAndFetch(AppointmentSchedule.class);
        }
    }

    /* =============================
     * BOOK SCHEDULE (TRANSACTION)
     * ============================= */
    public static boolean bookSchedule(int scheduleId, int patientId) {

        String sqlUpdateSlot = """
                UPDATE appointment_schedule 
                SET slot_sisa = slot_sisa - 1
                WHERE id = :sid AND slot_sisa > 0
                """;

        String sqlInsert = """
                INSERT INTO appointment (id_jadwal, id_pasien)
                VALUES (:sid, :pid)
                """;

        // Gunakan beginTransaction untuk operasi yang butuh commit/rollback
        try (Connection con = DatabaseConfig.getSql2o().beginTransaction()) {
            
            // 1. Kurangi Slot
            int updated = con.createQuery(sqlUpdateSlot)
                    .addParameter("sid", scheduleId)
                    .executeUpdate()
                    .getResult(); // Mengembalikan jumlah baris yang terupdate

            if (updated == 0) {
                // Jika 0, berarti slot habis atau ID salah. Tidak perlu rollback eksplisit,
                // karena kita belum commit, otomatis rollback saat try-resource selesai.
                return false;
            }

            // 2. Insert Appointment
            con.createQuery(sqlInsert)
                    .addParameter("sid", scheduleId)
                    .addParameter("pid", patientId)
                    .executeUpdate();

            // 3. Commit Transaksi (Wajib agar data tersimpan)
            con.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* =============================
     * HISTORY PASIEN
     * ============================= */
    public static List<Appointment> getHistoryByPatientId(int pasienId) {
        String sql = """
                SELECT a.id, d.nama AS dokter, d.spesialis,
                       s.tanggal, s.waktu_mulai AS waktu
                FROM appointment a
                JOIN appointment_schedule s ON a.id_jadwal = s.id
                JOIN login_dokter d ON s.id_dokter = d.id
                WHERE a.id_pasien = :pasienId
                ORDER BY s.tanggal DESC, s.waktu_mulai DESC
                """;

        try (Connection con = DatabaseConfig.getSql2o().open()) {
            // Pastikan class Appointment memiliki setter: setDokter, setSpesialis, setWaktu
            // agar mapping otomatis Sql2o bekerja.
            return con.createQuery(sql)
                    .addParameter("pasienId", pasienId)
                    .executeAndFetch(Appointment.class);
        }
    }

    /* =============================
     * DIAGNOSIS (Return List of Maps)
     * ============================= */
public static List<DiagnosisDoctor> getDiagnosisByPatientId(int id) {
        
        // Perhatikan penggunaan AS untuk mapping ke Model DiagnosisDoctor
        String sql = """
                SELECT 
                    id,
                    appointment_id AS appointmentId,
                    patient_name AS patientName,
                    patient_age AS patientAge,
                    gender,
                    date,
                    diagnosis,
                    prescription
                FROM diagnosis
                WHERE patient_name = (
                    SELECT nama FROM login_pasien WHERE id = :idParam
                )
                ORDER BY date DESC
                """;

        try (Connection con = DatabaseConfig.getSql2o().open()) {
            return con.createQuery(sql)
                    .addParameter("idParam", id)
                    .executeAndFetch(DiagnosisDoctor.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}