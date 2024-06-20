package com.example.library.controller;

import com.example.library.dto.login.LoginDto;
import com.example.library.dto.login.LoginResponseDto;
import com.example.library.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDto loginDto) {
        LoginResponseDto loginResponseDto = loginService.userLogin(loginDto);
        if (loginResponseDto == null) {
            return new ResponseEntity<>("Invalid login input", HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
        }
    }
}
