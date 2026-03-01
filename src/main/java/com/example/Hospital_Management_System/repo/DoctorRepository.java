package com.example.Hospital_Management_System.repo;

import com.example.Hospital_Management_System.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}