package com.example.library.service;

import com.example.library.dto.login.LoginDto;
import com.example.library.dto.login.LoginResponseDto;
import com.example.library.entity.User;
import com.example.library.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class LoginService {

    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    @Value("${jwt.token.key}")
    private String key;

    @Autowired
    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    public LoginResponseDto userLogin(LoginDto loginDto) {
        User user = userRepository.findByLogin(loginDto.getLogin());

        if (user != null && passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            var token = jwtService.generateToken(user);

            return new LoginResponseDto(token, user.getRole());
        } else {
            return null;
        }
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}
