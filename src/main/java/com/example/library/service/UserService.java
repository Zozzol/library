package com.example.library.service;

import com.example.library.dto.user.UserRequestDto;
import com.example.library.dto.user.UserResponseDto;
import com.example.library.entity.User;
import com.example.library.error.user.UserAlreadyExistsException;
import com.example.library.error.user.UserNotFoundException;
import com.example.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<String> addUser(UserRequestDto userRequestDto) {
        Optional<User> existingUser = Optional.ofNullable(userRepository.findByLogin(userRequestDto.getLogin()));
        if (existingUser.isPresent()) {
            throw UserAlreadyExistsException.create(userRequestDto.getLogin());
        } else {
//            if (userRequestDto.getLogin() == null || userRequestDto.getPassword() == null || userRequestDto.getRole() == null
//                    || userRequestDto.geteMail() == null || userRequestDto.getFullName() == null) {
//                return new ResponseEntity<>("None of the user parameters should be null.", HttpStatus.BAD_REQUEST);
//            }
            User user = new User();
            user.setLogin(userRequestDto.getLogin());
            user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
            user.setRole(userRequestDto.getRole());
            user.setFullName(userRequestDto.getFullName());
            userRepository.save(user);
            return new ResponseEntity<>("User added successfully", HttpStatus.OK);
        }
    }

    public ResponseEntity<String> deleteUser(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw UserNotFoundException.create(userId);
        } else {
            userRepository.deleteById(userId);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        }
    }

    public Iterable<UserResponseDto> getAllUsers() {
        Iterable<User> users = userRepository.findAll();
        return StreamSupport.stream(users.spliterator(), false)
                .map(user -> new UserResponseDto(user.getId(), user.getLogin(), user.getRole(), user.getFullName()))
                .collect(Collectors.toList());
    }

    public Optional<UserResponseDto> getUser(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User foundUser = user.get();
            UserResponseDto userResponseDto = new UserResponseDto(foundUser.getId(), foundUser.getLogin(), foundUser.getRole(), foundUser.getFullName());
            return Optional.of(userResponseDto);
        } else {
            throw UserNotFoundException.create(id);
        }
    }
}
