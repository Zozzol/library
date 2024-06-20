package com.example.library.dto.loan;

import java.sql.Date;

public class LoanResponseDto {
    private Integer id;
    private Date loanStartDate;
    private Date loanEndDate;
    private Date bookReturnDate;
    private Integer userId;
    private Integer bookId;

    // Constructor, Getters and Setters
    public LoanResponseDto(Integer id, Date loanStartDate, Date loanEndDate, Date bookReturnDate, Integer userId, Integer bookId) {
        this.id = id;
        this.loanStartDate = loanStartDate;
        this.loanEndDate = loanEndDate;
        this.bookReturnDate = bookReturnDate;
        this.userId = userId;
        this.bookId = bookId;
    }

    public Integer getId() {
        return id;
    }

    public Date getLoanStartDate() {
        return loanStartDate;
    }

    public Date getLoanEndDate() {
        return loanEndDate;
    }

    public Date getBookReturnDate() {
        return bookReturnDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getBookId() {
        return bookId;
    }
}
