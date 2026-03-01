package com.example.Hospital_Management_System.controller;

import com.example.Hospital_Management_System.dto.LoginRequestDto;
import com.example.Hospital_Management_System.dto.LoginResponseDto;
import com.example.Hospital_Management_System.dto.SignUpRequestDto;
import com.example.Hospital_Management_System.dto.SignUpResponseDto;
import com.example.Hospital_Management_System.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signup(@RequestBody SignUpRequestDto signRequestDto){
        return ResponseEntity.ok(authService.signup(signRequestDto));
    }
}
