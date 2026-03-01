package com.example.Hospital_Management_System.repo;

import com.example.Hospital_Management_System.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}