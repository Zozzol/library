package com.example.library.service;
import com.example.library.dto.LoginDto;
import com.example.library.dto.LoginResponseDto;
import com.example.library.dto.RegisterDto;
import com.example.library.dto.RegisterResponseDto;
import com.example.library.entity.Login;
import com.example.library.entity.User;
import com.example.library.error.BookNotFound;
import com.example.library.error.UserAlreadyExists;
import com.example.library.error.UsernameNotFound;
import com.example.library.repository.LoginRepository;
import com.example.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private final LoginRepository loginRepository;
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginService(LoginRepository loginRepository, UserRepository userRepository, JWTService jwtService, PasswordEncoder passwordEncoder) {
        this.loginRepository = loginRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResponseDto register(RegisterDto dto) {
        Optional<Login> existingLogin = loginRepository.findByUsername(dto.getUsername());

        if (existingLogin.isPresent()) {
            throw UserAlreadyExists.create(dto.getUsername());
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        userRepository.save(user);

        Login login = new Login();
        login.setPassword(passwordEncoder.encode(dto.getPassword()));
        login.setUsername(dto.getUsername());
        login.setRole(dto.getRole());
        login.setUser(user);

        loginRepository.save(login);

        return new RegisterResponseDto(login.getId(), login.getUsername(), login.getRole());
    }

    public LoginResponseDto login(LoginDto dto) {
        Optional<Login> existingLogin = loginRepository.findByUsername(dto.getUsername());

        if (existingLogin.isEmpty()) {
            throw UsernameNotFound.create(dto.getUsername());
        }

        Login login = loginRepository.findByUsername(dto.getUsername()).orElseThrow(RuntimeException::new);
        if(!passwordEncoder.matches(dto.getPassword(), login.getPassword())) {
            throw new RuntimeException();
        }
        String token = jwtService.generateToken(login);
        return new LoginResponseDto(token);
    }

}