package com.example.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.sql.Date;

/**
 * Class representing a loan in the library system.
 */
@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Date loanStartDate;
    private Date loanEndDate;
    private Date bookReturnDate;

    @JsonIgnoreProperties({"loans"})
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User userLoan;

    @JsonIgnoreProperties({"loans"})
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "book_id")
    private Book bookLoan;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getLoanStartDate() {
        return loanStartDate;
    }

    public void setLoanStartDate(Date loanStartDate) {
        this.loanStartDate = loanStartDate;
    }

    public Date getLoanEndDate() {
        return loanEndDate;
    }

    public void setLoanEndDate(Date loanEndDate) {
        this.loanEndDate = loanEndDate;
    }

    public Date getBookReturnDate() {
        return bookReturnDate;
    }

    public void setBookReturnDate(Date bookReturnDate) {
        this.bookReturnDate = bookReturnDate;
    }

    public User getUserLoan() {
        return userLoan;
    }

    public void setUserLoan(User userLoan) {
        this.userLoan = userLoan;
    }

    public Book getBookLoan() {
        return bookLoan;
    }

    public void setBookLoan(Book bookLoan) {
        this.bookLoan = bookLoan;
    }
}

