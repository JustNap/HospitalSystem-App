package com.untar.services;

import com.untar.repository.DiagnosisRepository;
import com.untar.repository.AppointmentRepository;
import com.untar.models.DiagnosisDoctor;
import com.untar.models.DoctorAppointment;

import java.sql.Date;
import java.util.List;

public class DokterService {

    private DiagnosisRepository diagnosisRepo = new DiagnosisRepository();
    private AppointmentRepository appointmentRepo = new AppointmentRepository();

    /* ====================================================
       JALUR 1: TABEL DIAGNOSIS (Jadwal)
       ==================================================== */
    
    public boolean saveToDiagnosis(DiagnosisDoctor d) {
        return diagnosisRepo.saveToDiagnosis(d);
    }

    public List<DiagnosisDoctor> getListDiagnosis() {
        return diagnosisRepo.getAllFromDiagnosis();
    }

    // PERBAIKAN DISINI: Pastikan memanggil diagnosisRepo.deleteFromDiagnosis
    public boolean deleteFromDiagnosis(int id) {
        return diagnosisRepo.deleteFromDiagnosis(id);
    }


    /* ====================================================
       JALUR 2: TABEL RIWAYAT (Lapor/Manual)
       ==================================================== */

    public boolean saveToRiwayat(DiagnosisDoctor d) {
        return diagnosisRepo.saveToRiwayat(d);
    }

    public List<DiagnosisDoctor> getListRiwayat() {
        return diagnosisRepo.getAllFromRiwayat();
    }
    
    public List<DiagnosisDoctor> getManualDiagnosis() {
        return diagnosisRepo.getManualDiagnosis();
    }

    // Method Hapus Riwayat
    public boolean deleteDiagnosis(int id) {
        return diagnosisRepo.deleteDiagnosis(id); 
    }


    /* ====================================================
       JALUR UMUM: APPOINTMENT
       ==================================================== */
    public List<DoctorAppointment> getTodayAppointments() {
        return appointmentRepo.getTodayAppointments();
    }

    public DoctorAppointment getAppointmentById(int id) {
        return appointmentRepo.getAppointmentById(id);
    }
    
    // Kompatibilitas
    public boolean inputDiagnosis(int appointmentId, String patientName, int age, String gender, Date date, String diagnosis, String prescription) {
        DiagnosisDoctor d = new DiagnosisDoctor(appointmentId, patientName, age, gender, date, diagnosis, prescription);
        return saveToRiwayat(d);
    }
}