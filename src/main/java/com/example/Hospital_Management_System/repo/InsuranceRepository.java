package com.example.Hospital_Management_System.repo;

import com.example.Hospital_Management_System.entities.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}