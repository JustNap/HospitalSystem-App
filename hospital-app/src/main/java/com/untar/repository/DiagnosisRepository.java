package com.untar.repository;

import com.untar.config.DatabaseConfig;
import com.untar.models.DiagnosisDoctor;
import org.sql2o.Connection;
import java.util.List;

public class DiagnosisRepository {

    // === 1. Dokter → Simpan ke tabel diagnosis ===
    public boolean saveToDiagnosis(DiagnosisDoctor diag) {
        String sql = "INSERT INTO diagnosis (appointment_id, patient_name, patient_age, gender, date, diagnosis, prescription) VALUES (:aptId, :name, :age, :gender, :date, :diag, :pres)";
        try (Connection con = DatabaseConfig.getSql2o().open()) {
            con.createQuery(sql)
                    .addParameter("aptId", diag.getAppointmentId())
                    .addParameter("name", diag.getPatientName())
                    .addParameter("age", diag.getPatientAge())
                    .addParameter("gender", diag.getGender())
                    .addParameter("date", diag.getDate())
                    .addParameter("diag", diag.getDiagnosis())
                    .addParameter("pres", diag.getPrescription())
                    .executeUpdate();
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // === 2. Dokter → Get semua diagnosis ===
    public List<DiagnosisDoctor> getAllFromDiagnosis() {
        String sql = "SELECT id, appointment_id AS appointmentId, patient_name AS patientName, patient_age AS patientAge, gender, date, diagnosis, prescription FROM diagnosis ORDER BY date DESC, id DESC";
        try (Connection con = DatabaseConfig.getSql2o().open()) {
            return con.createQuery(sql).executeAndFetch(DiagnosisDoctor.class);
        } catch (Exception e) { return null; }
    }

    // === 3. Pasien → Ambil diagnosis sesuai patient ID ===
    public List<DiagnosisDoctor> getDiagnosisByPatientId(int patientId) {
        String sql = """
            SELECT d.id,
                   d.appointment_id AS appointmentId,
                   d.patient_name AS patientName,
                   d.patient_age AS patientAge,
                   d.gender,
                   d.date,
                   d.diagnosis,
                   d.prescription
            FROM diagnosis d
            JOIN appointment a ON d.appointment_id = a.id
            WHERE a.id_pasien = :pid
            ORDER BY d.date DESC, d.id DESC
        """;

        try (Connection con = DatabaseConfig.getSql2o().open()) {
            return con.createQuery(sql)
                    .addParameter("pid", patientId)
                    .executeAndFetch(DiagnosisDoctor.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // === 4. Delete dari diagnosis ===
    public boolean deleteFromDiagnosis(int id) {
        String sql = "DELETE FROM diagnosis WHERE id = :id";
        try (Connection con = DatabaseConfig.getSql2o().open()) {
            return con.createQuery(sql).addParameter("id", id).executeUpdate().getResult() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ====== RIWAYAT (Manual Input) ======

    public boolean saveToRiwayat(DiagnosisDoctor diag) {
        String sql = "INSERT INTO riwayat (appointment_id, patient_name, patient_age, gender, date, diagnosis, prescription) VALUES (:aptId, :name, :age, :gender, :date, :diag, :pres)";
        try (Connection con = DatabaseConfig.getSql2o().open()) {
            con.createQuery(sql)
                    .addParameter("aptId", diag.getAppointmentId())
                    .addParameter("name", diag.getPatientName())
                    .addParameter("age", diag.getPatientAge())
                    .addParameter("gender", diag.getGender())
                    .addParameter("date", diag.getDate())
                    .addParameter("diag", diag.getDiagnosis())
                    .addParameter("pres", diag.getPrescription())
                    .executeUpdate();
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public List<DiagnosisDoctor> getManualDiagnosis() {
        String sql = "SELECT id, appointment_id AS appointmentId, patient_name AS patientName, patient_age AS patientAge, gender, date, diagnosis, prescription FROM riwayat WHERE appointment_id = 0 ORDER BY id DESC";
        try (Connection con = DatabaseConfig.getSql2o().open()) {
            return con.createQuery(sql).executeAndFetch(DiagnosisDoctor.class);
        } catch (Exception e) { return null; }
    }

    public List<DiagnosisDoctor> getAllFromRiwayat() {
        String sql = "SELECT id, appointment_id AS appointmentId, patient_name AS patientName, patient_age AS patientAge, gender, date, diagnosis, prescription FROM riwayat ORDER BY date DESC, id DESC";
        try (Connection con = DatabaseConfig.getSql2o().open()) {
            return con.createQuery(sql).executeAndFetch(DiagnosisDoctor.class);
        } catch (Exception e) { return null; }
    }

    public boolean deleteDiagnosis(int id) {
        String sql = "DELETE FROM riwayat WHERE id = :id";
        try (Connection con = DatabaseConfig.getSql2o().open()) {
            return con.createQuery(sql).addParameter("id", id).executeUpdate().getResult() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
