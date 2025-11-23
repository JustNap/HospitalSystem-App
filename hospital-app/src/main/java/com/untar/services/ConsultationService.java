package com.untar.services;

import com.untar.models.Consultation;
import com.untar.repository.ConsultationRepository;

import java.util.List;

public class ConsultationService {

    public ConsultationService() {
    }

    public List<Consultation> getAllConsultations() {
        return ConsultationRepository.getAll();
    }
}