package com.example.Hospital_Management_System.entities;

import com.example.Hospital_Management_System.entities.type.BloodGroupType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(
        name = "patient_table",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_patient_email", columnNames = {"email"}),
                @UniqueConstraint(name = "unique_patient_id",columnNames = {"id"})
        },
        indexes = {
                @Index(name = "idx_patient_birth_date" ,columnList = "birth_date")
        }
)
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private LocalDate birthDate;
    private String email;
    @Column(nullable = false)
    private String gender;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createAt;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BloodGroupType bloodGroupType;
    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})//CascadeType is an option used to define how an operation
                                                                // performed on a "parent" entity should be propagated to its "child" (associated) entities.
    private Insurance insurance;//owning side
    @OneToMany(mappedBy = "patient",cascade = CascadeType.REMOVE)//inverse side
    @ToString.Exclude
    private List<Appointment> appointment = new ArrayList<>();
}
