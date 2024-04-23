package com.example.library.controller;

import com.example.library.commonTypes.UserRole;
import com.example.library.dto.RegisterDto;
import com.example.library.dto.RegisterResponseDto;
import com.example.library.entity.Login;
import com.example.library.entity.User;
import com.example.library.repository.UserRepository;
import com.example.library.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
//@PreAuthorize("permitAll()")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final LoginService loginService;

    @Autowired
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, LoginService loginService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.loginService = loginService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterDto requestBody) {
        RegisterResponseDto dto = loginService.register(requestBody);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
