package com.example.library.error.loan;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

public class LoanNotFoundException extends RuntimeException {

    private LoanNotFoundException(String message) {
        super(message);
    }

    public static ResponseStatusException create(Integer loanId) {
        LoanNotFoundException exception = new LoanNotFoundException(String.format("Loan with ID %d not found.", loanId));
        return new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
    }

    public static ResponseStatusException create(String message) {
        LoanNotFoundException exception = new LoanNotFoundException(message);
        return new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
    }
}
