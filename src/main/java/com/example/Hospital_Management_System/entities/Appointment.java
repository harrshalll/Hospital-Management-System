package com.example.Hospital_Management_System.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime appointmentTime;
    @Column(length = 500)
    private String reason;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Patient patient;//owning side
    @ManyToOne
    @JoinColumn(nullable = false)
    private Doctor doctor;//owning side
}
