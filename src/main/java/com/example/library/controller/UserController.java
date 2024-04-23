package com.example.library.controller;

import com.example.library.commonTypes.UserRole;
import com.example.library.entity.Login;
import com.example.library.entity.User;
import com.example.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addUser(@RequestBody User user, @RequestParam String password) {
        Login login = new Login();
        login.setUsername(user.getName());
        login.setPassword(passwordEncoder.encode(password));
        login.setRole(UserRole.ROLE_READER);
        login.setUser(user);

        user.setLogin(login);
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok("User added successfully with ID: " + savedUser.getId());
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
