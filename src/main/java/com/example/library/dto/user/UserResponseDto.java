package com.example.library.dto.user;

import com.example.library.commonTypes.UserRole;

public class UserResponseDto {
    private Integer id;
    private String login;
    private UserRole role;

    private String fullName;

    // Constructor
    public UserResponseDto(Integer id, String login, UserRole role, String fullName) {
        this.id = id;
        this.login = login;
        this.role = role;
        this.fullName = fullName;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public UserRole getRole() {
        return role;
    }

    public String getFullName() {
        return fullName;
    }
}
