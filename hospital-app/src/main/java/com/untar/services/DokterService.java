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

    
    public boolean saveToDiagnosis(DiagnosisDoctor d) {
        return diagnosisRepo.saveToDiagnosis(d);
    }

    public List<DiagnosisDoctor> getListDiagnosis() {
        return diagnosisRepo.getAllFromDiagnosis();
    }

    public boolean deleteFromDiagnosis(int id) {
        return diagnosisRepo.deleteFromDiagnosis(id);
    }


    public boolean saveToRiwayat(DiagnosisDoctor d) {
        return diagnosisRepo.saveToRiwayat(d);
    }

    public List<DiagnosisDoctor> getListRiwayat() {
        return diagnosisRepo.getAllFromRiwayat();
    }
    
    public List<DiagnosisDoctor> getManualDiagnosis() {
        return diagnosisRepo.getManualDiagnosis();
    }

    public boolean deleteDiagnosis(int id) {
        return diagnosisRepo.deleteDiagnosis(id); 
    }

    public List<DoctorAppointment> getTodayAppointments() {
        return appointmentRepo.getTodayAppointments();
    }

    public DoctorAppointment getAppointmentById(int id) {
        return appointmentRepo.getAppointmentById(id);
    }
    
    public boolean inputDiagnosis(int appointmentId, String patientName, int age, String gender, Date date, String diagnosis, String prescription) {
        DiagnosisDoctor d = new DiagnosisDoctor(appointmentId, patientName, age, gender, date, diagnosis, prescription);
        return saveToRiwayat(d);
    }
}