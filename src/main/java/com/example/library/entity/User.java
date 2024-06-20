package com.example.library.entity;

import com.example.library.commonTypes.UserRole;
import jakarta.persistence.*;

import java.util.List;



@Entity
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic
    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Basic
    @Column(name = "password", nullable = false)
    private String password;

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;


    @Basic
    @Column(name = "fullName")
    private String fullName;

    @OneToMany(mappedBy = "userLoan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Loan> bookLoanList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Loan> getBookLoanList() {
        return bookLoanList;
    }

    public void setBookLoanList(List<Loan> bookLoanList) {
        this.bookLoanList = bookLoanList;
    }
}
