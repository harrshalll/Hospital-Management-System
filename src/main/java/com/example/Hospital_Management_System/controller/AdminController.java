package com.example.Hospital_Management_System.controller;

import com.example.Hospital_Management_System.dto.PatientResponseDto;
import com.example.Hospital_Management_System.entities.Patient;
import com.example.Hospital_Management_System.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final PatientService patientService;
    @GetMapping("/patients")
    public ResponseEntity<List<PatientResponseDto>> getAllPatient(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "size", defaultValue = "10") Integer pageSize){
     return ResponseEntity.ok(patientService.getAllPatient(pageNumber,pageSize));
    }
}
