package com.example.library.controller;

import com.example.library.dto.user.UserRequestDto;
import com.example.library.dto.user.UserResponseDto;
import com.example.library.error.user.UserAlreadyExistsException;
import com.example.library.error.user.UserNotFoundException;
import com.example.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public ResponseEntity<String> addUser(@RequestBody UserRequestDto userRequestDto) {
        return userService.addUser(userRequestDto);
    }

    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId) {
        return userService.deleteUser(userId);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public Iterable<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/getUser/{userId}")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public @ResponseBody Optional<UserResponseDto> getUser(@PathVariable Integer userId) {
        return userService.getUser(userId);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
