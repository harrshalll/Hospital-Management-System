package com.example.Hospital_Management_System.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(length = 50)
    private String specialization;
    @Column(nullable = false,unique = true,length = 100)
    private String email;
    @ManyToMany(mappedBy = "doctors",fetch = FetchType.EAGER)
    private Set<Department> departments = new HashSet<>();
    @OneToMany(fetch = FetchType.EAGER)
    private List<Appointment> appointmentList = new ArrayList<>();

    @OneToOne
    @MapsId
    private User user;
}
