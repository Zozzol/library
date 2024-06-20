package com.example.library.controller;

import com.example.library.dto.loan.LoanRequestDto;
import com.example.library.dto.loan.LoanResponseDto;
import com.example.library.entity.Loan;
import com.example.library.error.loan.LoanAlreadyExistsException;
import com.example.library.error.loan.LoanNotFoundException;
import com.example.library.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/loan")
public class LoanController {
    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public ResponseEntity<String> addLoan(@RequestBody LoanRequestDto loanRequestDto) {
        return loanService.addLoan(loanRequestDto);
    }

    @DeleteMapping("/delete/{loanId}")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public ResponseEntity<String> deleteLoan(@PathVariable Integer loanId) {
        return loanService.deleteLoan(loanId);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public @ResponseBody Iterable<LoanResponseDto> getAllLoans() {
        return loanService.getAllLoans();
    }

    @GetMapping("/getMyLoans")
    @PreAuthorize("hasAnyRole('ROLE_READER')")
    public @ResponseBody List<LoanResponseDto> getMyLoans() {
        return loanService.getMyLoans();
    }

    @PostMapping("/returnDate/{loanID}/{date}")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public ResponseEntity<String> updateBookReturnDate(@PathVariable Integer loanID, @PathVariable Date date) {
        return loanService.updateBookReturnDate(loanID, date);
    }

    @ExceptionHandler(LoanNotFoundException.class)
    public ResponseEntity<String> handleLoanNotFoundException(LoanNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LoanAlreadyExistsException.class)
    public ResponseEntity<String> handleLoanAlreadyExistsException(LoanAlreadyExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
