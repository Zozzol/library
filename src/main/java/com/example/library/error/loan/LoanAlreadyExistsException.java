package com.example.library.error.loan;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

public class LoanAlreadyExistsException extends RuntimeException {

    private LoanAlreadyExistsException(String message) {
        super(message);
    }

    public static ResponseStatusException create(Integer loanId) {
        LoanAlreadyExistsException exception = new LoanAlreadyExistsException(String.format("Loan with ID %d already exists.", loanId));
        return new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage(), exception);
    }
}
