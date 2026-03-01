package com.example.Hospital_Management_System.repo;

import com.example.Hospital_Management_System.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}