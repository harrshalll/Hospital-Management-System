package com.example.Hospital_Management_System;

import com.example.Hospital_Management_System.entities.Insurance;
import com.example.Hospital_Management_System.entities.Patient;
import com.example.Hospital_Management_System.services.InsuranceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class InsuranceTest {

    @Autowired
    private InsuranceService insuranceService;
    @Test
    public void insuranceTest(){
        Insurance insurance = Insurance.builder()
                .policyNumber("POLICY_123")
                .provider("SBI")
                .validUntil(LocalDate.of(2030,12,12))
                .build();

        Patient patient = insuranceService.assignInsuranceToPatient(insurance,1L);
        System.out.println(patient);
    }
}
