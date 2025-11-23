package com.untar.controllers;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.untar.models.AppointmentSchedule;
import com.untar.models.Consultation;
import com.untar.models.ManageDoctor;
import com.untar.models.Patient;
import com.untar.repository.AppointmentScheduleRepository;
import com.untar.repository.PatientRepository;
import com.untar.services.ConsultationService;
import com.untar.services.ManageDoctorService;

import static spark.Spark.get;
import static spark.Spark.post;

public class AdminController {

    private static final ManageDoctorService doctorService = new ManageDoctorService();
    private static final ConsultationService consultationService = new ConsultationService();

    public static void init() {
        get("/admin/dashboard", (req, res) -> {
            System.out.println("LOG: Mengakses Dashboard");
            return renderHTML("/templates/admin/dashboard.html");
        });

        get("/admin/doctors-list", (req, res) -> {
            System.out.println("LOG: Mengakses Doctor List");
            try {
                List<ManageDoctor> doctors = doctorService.getAllDoctors();
                StringBuilder rows = new StringBuilder();

                if (doctors != null) {
                    for (ManageDoctor d : doctors) {
                        rows.append("<tr>")
                                .append("<td>").append(d.getName()).append("</td>")
                                .append("<td>").append(d.getSpeciality()).append("</td>")
                                .append("<td>").append(d.getPhoneNumber()).append("</td>")
                                .append("</tr>");
                    }
                }

                return renderHTML("/templates/admin/doctors-list.html")
                        .replace("{{doctorsRows}}", rows.toString());

            } catch (Exception e) {
                e.printStackTrace();
                return "Error di Doctor List: " + e.getMessage();
            }
        });

        get("/admin/patients-list", (req, res) -> {
            System.out.println("LOG: Mengakses Patient List");
            try {
                List<Patient> patients = PatientRepository.getAll();
                StringBuilder rows = new StringBuilder();

                if (patients != null) {
                    for (Patient p : patients) {
                        rows.append("<tr>")
                                .append("<td>").append(p.getNama()).append("</td>")
                                .append("<td>").append(p.getEmail()).append("</td>")
                                .append("<td>").append(p.getNomor_hp()).append("</td>")
                                .append("<td>").append(p.getPassword() == null ? "-" : p.getPassword()).append("</td>")
                                .append("</tr>");
                    }
                }

                return renderHTML("/templates/admin/patients-list.html")
                        .replace("{{patientsRows}}", rows.toString());
            } catch (Exception e) {
                e.printStackTrace();
                return "Error di Patient List: " + e.getMessage();
            }
        });


        get("/admin/manage-doctors", (req, res) -> {
            System.out.println("LOG: Mengakses Manage Doctors");

            try {
                List<ManageDoctor> doctors = doctorService.getAllDoctors();
                StringBuilder rows = new StringBuilder();

                if (doctors != null) {
            for (ManageDoctor d : doctors) {
                rows.append("<tr>")
                        .append("<td>").append(d.getName()).append("</td>")
                        .append("<td>").append(d.getSpeciality()).append("</td>")
                        .append("<td>").append(d.getPhoneNumber() == null ? "-" : d.getPhoneNumber()).append("</td>")
                        .append("<td>")
                        .append("<form method='post' action='/admin/manage-doctors/delete' style='display:inline;'>")
                        .append("<input type='hidden' name='id' value='").append(d.getId()).append("'>")
                        .append("<button type='submit' class='delete-btn' style='background:#ff4d4d; color:white; border:none; padding:8px 12px; border-radius:4px; cursor:pointer;'>Hapus</button>")
                        .append("</form>")
                        .append("</td>")
                        .append("</tr>");
            }
                }
                return renderHTML("/templates/admin/admin-doctors.html")
                        .replace("{{doctorsRows}}", rows.toString());

            } catch (Exception e) {
                e.printStackTrace();
                return "Error 500 di Manage Doctors: " + e.getMessage();
            }
        });

        post("/admin/manage-doctors/add", (req, res) -> {
            System.out.println("LOG: Menambah Dokter Baru...");
            String name = req.queryParams("doctorName");
            String speciality = req.queryParams("doctorSpec");
            String phone = req.queryParams("doctorPhone"); 
            doctorService.addDoctor(name, speciality, phone);
            res.redirect("/admin/manage-doctors");
            return null;
        });

        post("/admin/manage-doctors/delete", (req, res) -> {
            System.out.println("LOG: Menghapus Dokter ID: " + req.queryParams("id"));
            String id = req.queryParams("id");
            
            doctorService.deleteDoctor(id);
            
            res.redirect("/admin/manage-doctors");
            return null;
        });

        get("/admin/appointment-doctors", (req, res) -> {
            System.out.println("LOG: Mengakses Appointment Schedule");

            try {
                List<AppointmentSchedule> schedules = AppointmentScheduleRepository.getAll();
                List<ManageDoctor> doctors = doctorService.getAllDoctors();
                StringBuilder doctorOptions = new StringBuilder();
                if (doctors != null) {
                    for (ManageDoctor d : doctors) {
                        doctorOptions.append("<option value='").append(d.getId()).append("'>")
                                .append("Dr. ").append(d.getName())
                                .append(" (").append(d.getSpeciality()).append(")")
                                .append("</option>");
                    }
                }

                // 2. Buat Tabel Jadwal
                StringBuilder rows = new StringBuilder();
                if (schedules != null) {
                    for (AppointmentSchedule s : schedules) {
                        rows.append("<tr>")
                                .append("<td>").append(s.getNama_dokter()).append("</td>")
                                .append("<td>").append(s.getTanggal()).append("</td>")
                                .append("<td>").append(s.getWaktu_mulai()).append("</td>")
                                .append("<td>").append(s.getWaktu_selesai()).append("</td>")
                                .append("<td>").append(s.getSlot_total()).append("</td>")
                                .append("<td>")
                                .append("<form method='post' action='/admin/schedule/delete' style='display:inline;'>")
                                .append("<input type='hidden' name='id' value='").append(s.getId()).append("'>")
                                .append("<button type='submit' style='background:#ff4d4d; color:white; border:none; padding:5px 10px; cursor:pointer;'>Hapus</button>")
                                .append("</form>")
                                .append("</td>")
                                .append("</tr>");
                    }
                }

                return renderHTML("/templates/admin/appointment-doctors.html")
                        .replace("{{doctorOptions}}", doctorOptions.toString())
                        .replace("{{scheduleRows}}", rows.toString());

            } catch (Exception e) {
                e.printStackTrace();
                return "Error di Schedule: " + e.getMessage();
            }
        });

        // ACTION: CREATE SCHEDULE
        post("/admin/appointment-doctors/create", (req, res) -> {
            int doctorId = Integer.parseInt(req.queryParams("id_doctor"));
            String tanggal = req.queryParams("tanggal");
            String mulai = req.queryParams("mulai");
            String selesai = req.queryParams("selesai");
            int slot = Integer.parseInt(req.queryParams("slot"));

            AppointmentScheduleRepository.save(doctorId, tanggal, mulai, selesai, slot);
            res.redirect("/admin/appointment-doctors");
            return null;
        });

        // ACTION: DELETE SCHEDULE
        post("/admin/schedule/delete", (req, res) -> {
            int id = Integer.parseInt(req.queryParams("id"));
            AppointmentScheduleRepository.delete(id);
            res.redirect("/admin/appointment-doctors");
            return null;
        });

        // =============================================================
        // 6. CONSULTATION REPORT (Appointment Details)
        // URL: http://localhost:4567/admin/consultations
        // =============================================================
        get("/admin/consultations", (req, res) -> {
            System.out.println("LOG: Mengakses Consultation Report");
            
            try {
                List<Consultation> consultations = consultationService.getAllConsultations();
                StringBuilder rows = new StringBuilder();

                if (consultations != null) {
                    for (Consultation c : consultations) {
                        rows.append("<tr>")
                                .append("<td>").append(c.getConsultation_date()).append("</td>")
                                .append("<td>").append(c.getDoctorName()).append("</td>")
                                .append("<td>").append(c.getPatientName()).append("</td>")
                                .append("<td>").append(c.getDiagnosis()).append("</td>")
                                // Handle Null Notes agar tidak error/jelek
                                .append("<td>").append(c.getNotes() == null ? "-" : c.getNotes()).append("</td>")
                                .append("</tr>");
                    }
                } else {
                    rows.append("<tr><td colspan='5'>Belum ada data konsultasi</td></tr>");
                }

                // Menggunakan template baru admin-consultations.html (BUKAN yang ada #foreach)
                return renderHTML("/templates/admin/admin-consultations.html")
                        .replace("{{consultRows}}", rows.toString());

            } catch (Exception e) {
                e.printStackTrace(); // Cek Terminal jika 500
                return "Error 500 di Consultation: " + e.getMessage();
            }
        });
    }

    // =============================================================
    // UTILITY: LOAD HTML FILE
    // =============================================================
    private static String renderHTML(String path) {
        try (InputStream input = AdminController.class.getResourceAsStream(path)) {
            if (input == null) {
                System.err.println("❌ ERROR 404: File template tidak ketemu di: " + path);
                return "<h1>Error 404: Template Not Found</h1><p>Path: " + path + "</p>";
            }
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return "<h1>Error 500: Gagal Load Template</h1><p>" + e.getMessage() + "</p>";
        }
    }
}