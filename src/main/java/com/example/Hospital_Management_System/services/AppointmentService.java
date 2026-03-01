package com.example.Hospital_Management_System.services;

import com.example.Hospital_Management_System.entities.Appointment;
import com.example.Hospital_Management_System.entities.Doctor;
import com.example.Hospital_Management_System.entities.Patient;
import com.example.Hospital_Management_System.repo.AppointmentRepository;
import com.example.Hospital_Management_System.repo.DoctorRepository;
import com.example.Hospital_Management_System.repo.PatientRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepo patientRepo;
    private final DoctorRepository doctorRepository;
    @Transactional
    public Appointment createAppointment(Appointment appointment, Long patientId,Long doctorId){
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor With ID: "+doctorId+ " Not Found"));
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient With ID: "+patientId+ " Not Found"));
        if(appointment.getId() != null)throw new IllegalArgumentException("Appointment Should Not have been there");
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

//        patient.getAppointment().add(appointment);//Bidirectional Mapping
//        doctor.getAppointmentList().add(appointment);//Bidirectional Mapping

        appointmentRepository.save(appointment);
        return appointment;
    }
}
