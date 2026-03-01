package com.example.Hospital_Management_System.services;

import com.example.Hospital_Management_System.dto.PatientResponseDto;
import com.example.Hospital_Management_System.entities.Patient;
import com.example.Hospital_Management_System.repo.PatientRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PatientService {

    @Autowired
    private PatientRepo patientRepo;
    @Autowired
    private ModelMapper modelMapper;

    public Page<Patient> getAllPatient(){
        Page<Patient> patientList = patientRepo.findByAll(PageRequest.of(0,3));
        return patientList;
    }
    public List<PatientResponseDto> getAllPatient(Integer pageNumber, Integer pageSize){
        return patientRepo.findAll(PageRequest.of(pageNumber,pageSize))
                .stream()
                .map(patient -> modelMapper.map(patient, PatientResponseDto.class))
                .collect(Collectors.toList());
    }
}

