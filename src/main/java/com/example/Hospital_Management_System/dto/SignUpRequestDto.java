package com.example.Hospital_Management_System.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class SignUpRequestDto {
    private String username;
    private String password;
}
