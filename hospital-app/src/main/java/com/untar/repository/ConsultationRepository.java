package com.untar.repository;

import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import com.untar.models.Consultation;

public class ConsultationRepository {

    private Sql2o sql2o;

    public ConsultationRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public List<Consultation> findAll() {
        String sql =
            "SELECT " +
            "  c.id, " +
            "  c.consultation_date, " +
            "  c.diagnosis, " +
            "  c.notes, " +
            "  d.nama AS doctorName, " +
            "  CAST(c.patient_id AS CHAR) AS patientName " + 
            "FROM consultations c " +
            "LEFT JOIN login_dokter d ON c.doctor_id = d.id"; 

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                .executeAndFetch(Consultation.class);
        }
    }
}