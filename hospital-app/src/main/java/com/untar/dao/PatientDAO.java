package com.untar.dao;

import com.untar.config.DatabaseConfig;
import com.untar.models.Appointment;
import com.untar.models.AppointmentSchedule;
import com.untar.models.DiagnosisDoctor;
import com.untar.models.Doctor;
import com.untar.repository.DiagnosisRepository;
import org.sql2o.Connection;

import java.util.List;

public class PatientDAO {

    private static DiagnosisRepository diagRepo = new DiagnosisRepository();

    public static List<Doctor> getAllDoctors() {
        String sql = "SELECT id, nama, spesialis, nomor_hp FROM login_dokter";

        try (Connection con = DatabaseConfig.getSql2o().open()) {
            return con.createQuery(sql).executeAndFetch(Doctor.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

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
                    .addParameter("doctorId", doctorId)
                    .executeAndFetch(AppointmentSchedule.class);
        }
    }

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

        try (Connection con = DatabaseConfig.getSql2o().beginTransaction()) {

            int updated = con.createQuery(sqlUpdateSlot)
                    .addParameter("sid", scheduleId)
                    .executeUpdate()
                    .getResult();

            if (updated == 0) return false;

            con.createQuery(sqlInsert)
                    .addParameter("sid", scheduleId)
                    .addParameter("pid", patientId)
                    .executeUpdate();

            con.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Appointment> getHistoryByPatientId(int pasienId) {
        String sql = """
                SELECT a.id,
                       d.nama AS dokter,
                       d.spesialis,
                       s.tanggal,
                       s.waktu_mulai AS waktu
                FROM appointment a
                JOIN appointment_schedule s ON a.id_jadwal = s.id
                JOIN login_dokter d ON s.id_dokter = d.id
                WHERE a.id_pasien = :pasienId
                ORDER BY s.tanggal DESC, s.waktu_mulai DESC
                """;

        try (Connection con = DatabaseConfig.getSql2o().open()) {
            return con.createQuery(sql)
                    .addParameter("pasienId", pasienId)
                    .executeAndFetch(Appointment.class);
        }
    }

    // === Pasien hanya akses diagnosis miliknya sendiri ===
    public static List<DiagnosisDoctor> getDiagnosisByPatientId(int idPasien) {
        return diagRepo.getDiagnosisByPatientId(idPasien);
    }
}
