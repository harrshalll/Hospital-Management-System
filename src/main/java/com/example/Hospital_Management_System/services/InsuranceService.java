package com.example.Hospital_Management_System.services;

import com.example.Hospital_Management_System.entities.Insurance;
import com.example.Hospital_Management_System.entities.Patient;
import com.example.Hospital_Management_System.repo.InsuranceRepository;
import com.example.Hospital_Management_System.repo.PatientRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceService {
    private final InsuranceRepository insuranceRepository;
    private final PatientRepo patientRepo;

    @Transactional
    public Patient assignInsuranceToPatient(Insurance insurance, Long patientId){
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient with Id Not Found: " +patientId));
        patient.setInsurance(insurance);

        insurance.setPatient(patient);//This is for bidirectional consistency maintenance.

        return patient;
    }
}
