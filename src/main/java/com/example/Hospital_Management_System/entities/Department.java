package com.example.Hospital_Management_System.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true,length = 100)
    private String departmentName;
    @OneToOne
    private Doctor headDoctor;
    @ManyToMany
    private Set<Doctor> doctors = new HashSet<>();
}
