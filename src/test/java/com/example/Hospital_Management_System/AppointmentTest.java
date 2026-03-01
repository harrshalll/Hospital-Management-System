package com.example.Hospital_Management_System;

import com.example.Hospital_Management_System.entities.Appointment;
import com.example.Hospital_Management_System.services.AppointmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class AppointmentTest {
    @Autowired
    private AppointmentService appointmentService;
    @Test
    public void createAppointment(){
        Appointment appointment = Appointment.builder()
                .appointmentTime(LocalDateTime.of(2025,06,01,15,30))
                .reason("Fever")
                .build();
        System.out.println(appointmentService.createAppointment(appointment,1L,1L));
    }
}
