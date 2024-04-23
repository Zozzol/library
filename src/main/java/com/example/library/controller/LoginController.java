package com.example.library.controller;

import com.example.library.dto.LoginDto;
import com.example.library.dto.LoginResponseDto;
import com.example.library.dto.RegisterDto;
import com.example.library.dto.RegisterResponseDto;
import com.example.library.entity.Login;
import com.example.library.service.LoginService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService){
        this.loginService = loginService;
    }
    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto requestBody) {
        LoginResponseDto dto = loginService.login(requestBody);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterDto requestBody) {
        RegisterResponseDto dto = loginService.register(requestBody);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


}
