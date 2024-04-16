//package com.example.library.service;
//
//import com.example.library.LoginForm;
//import com.example.library.security.PasswordEncoderConfig;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//
//@Service
//public class LoginService {
//
//    private final PasswordEncoder passwordEncoder;
//
//    @Value("${jwt.token.key}")
//    private String key;
//
//    @Autowired
//    public LoginService(PasswordEncoder passwordEncoder/*, UserRepository userRepository*/){
//        this.passwordEncoder = passwordEncoder;
//        //this.UserRepository = userRepository;
//    }
//    public String login(LoginForm loginForm){
//        //User user = userRepository.getUserByLogin(loginForm.getForm());
//        if(passwordEncoder.matches(loginForm.getPassword(),/*user.getPassword()*/"")){
//            long timeMillis = System.currentTimeMillis();
//            String token = Jwts.builder()
//                    .issuedAt(new Date(timeMillis))
//                    .expiration(new Date(timeMillis+5*60*1000))
//                    .claim("id", "userId")
//                    .claim("userRole", "ROLE_")
//                    .signWith(SignatureAlgorithm.HS256, key)
//                    .compact();
//            return token;
//        }else{
//            return null;
//        }
//    }
//}
