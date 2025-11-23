package com.untar.services;

import java.util.List;

import org.sql2o.Sql2o;

import com.untar.config.DatabaseConfig;
import com.untar.models.Consultation;
import com.untar.repository.ConsultationRepository;

public class ConsultationService {

    private ConsultationRepository consultationRepository;
    private Sql2o sql2o;

    public ConsultationService() {
        this.sql2o = DatabaseConfig.getSql2o();
        this.consultationRepository = new ConsultationRepository(this.sql2o);
    }

    public List<Consultation> getAllConsultations() {
        return consultationRepository.findAll();
    }
}