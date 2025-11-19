package com.untar.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.untar.models.Appointment;
import com.untar.models.Doctor;

public class PatientDAO {

    private static Connection connect() throws Exception {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/db_hospital",
                "root",
                ""
        );
    }

    public static List<Doctor> getAllDoctors() {
        List<Doctor> list = new ArrayList<>();

        try {
            Connection conn = connect();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM login_dokter");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Doctor(
                        rs.getInt("id"),
                        rs.getString("nama"),
                        rs.getString("spesialis"),
                        rs.getInt("nomorHP")
                ));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void saveAppointment(int idDokter, int idPasien, String date, String time) {
        try {
            Connection conn = connect();
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO jadwal (id, id_dokter, id_pasien, date, time) VALUES (NULL, ?, ?, ?, ?)"
            );
            ps.setInt(1, idDokter);
            ps.setInt(2, idPasien);
            ps.setString(3, date);
            ps.setString(4, time);
            ps.executeUpdate();

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Appointment> getHistoryByPatientId(int pasienId) {
        List<Appointment> list = new ArrayList<>();

        try {
            Connection conn = connect();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT j.id, j.date, j.time, d.nama as dokter, d.spesialis " +
                            "FROM jadwal j JOIN login_dokter d ON j.id_dokter = d.id " +
                            "WHERE j.id_pasien = ?"
            );
            ps.setInt(1, pasienId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Appointment(
                        rs.getInt("id"),
                        rs.getString("dokter"),
                        rs.getString("spesialis"),
                        rs.getString("date"),
                        rs.getString("time")
                ));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}