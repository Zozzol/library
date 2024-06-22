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

    @Value("${jwt.token.key}")
    private String key;

    @Autowired
    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public LoginResponseDto userLogin(LoginDto loginDto) {
        User user = userRepository.findByLogin(loginDto.getLogin());

        if (user != null && passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            long timeMillis = System.currentTimeMillis();
            String token = Jwts.builder()
                    .issuedAt(new Date(timeMillis))
                    .expiration(new Date(timeMillis + 5 * 60 * 1000))
                    .claim("id", user.getId())
                    .claim("role", user.getRole())
                    .signWith(SignatureAlgorithm.HS256, key)
                    .compact();

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
