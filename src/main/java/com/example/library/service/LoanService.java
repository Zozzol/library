package com.example.library.service;

import com.example.library.dto.loan.LoanRequestDto;
import com.example.library.dto.loan.LoanResponseDto;
import com.example.library.entity.Loan;
import com.example.library.entity.User;
import com.example.library.error.loan.LoanAlreadyExistsException;
import com.example.library.error.loan.LoanNotFoundException;
import com.example.library.repository.LoanRepository;
import com.example.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository, UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> addLoan(LoanRequestDto loanRequestDto) {
        Optional<Loan> existingLoan = loanRepository.findById(loanRequestDto.getId());
        if (existingLoan.isPresent()) {
            throw LoanAlreadyExistsException.create(loanRequestDto.getId());
        } else {
            if (loanRequestDto.getLoanEndDate().before(loanRequestDto.getLoanStartDate())) {
                return new ResponseEntity<>("End date should be after start date.", HttpStatus.BAD_REQUEST);
            }
            if (loanRequestDto.getUserId() == null || loanRequestDto.getBookId() == null) {
                return new ResponseEntity<>("User/Book ID cannot be null.", HttpStatus.BAD_REQUEST);
            }
            Optional<User> userOptional = userRepository.findById(loanRequestDto.getUserId());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                Loan loan = new Loan();
                loan.setId(loanRequestDto.getId());
                loan.setLoanStartDate(loanRequestDto.getLoanStartDate());
                loan.setLoanEndDate(loanRequestDto.getLoanEndDate());
                loan.setBookReturnDate(loanRequestDto.getBookReturnDate());
                loan.setUserLoan(user);
                loanRepository.save(loan);
                return new ResponseEntity<>("Loan added successfully", HttpStatus.OK);
            } else {
                throw LoanNotFoundException.create(loanRequestDto.getUserId());
            }
        }
    }

    public ResponseEntity<String> deleteLoan(Integer loanId) {
        Optional<Loan> existingLoan = loanRepository.findById(loanId);
        if (existingLoan.isEmpty()) {
            throw LoanNotFoundException.create(loanId);
        } else {
            loanRepository.deleteById(loanId);
            return new ResponseEntity<>("Loan deleted successfully", HttpStatus.OK);
        }
    }

    public Iterable<LoanResponseDto> getAllLoans() {
        Iterable<Loan> loans = loanRepository.findAll();
        return StreamSupport.stream(loans.spliterator(), false)
                .map(loan -> new LoanResponseDto(loan.getId(), loan.getLoanStartDate(), loan.getLoanEndDate(), loan.getBookReturnDate(), loan.getUserLoan().getId(), loan.getBookLoan().getId()))
                .collect(Collectors.toList());
    }

    public List<LoanResponseDto> getMyLoans() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String id = (String) authentication.getPrincipal();
            Optional<User> userOptional = userRepository.findById(Integer.valueOf(id));
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                return user.getBookLoanList().stream()
                        .map(loan -> new LoanResponseDto(loan.getId(), loan.getLoanStartDate(), loan.getLoanEndDate(), loan.getBookReturnDate(), loan.getUserLoan().getId(), loan.getBookLoan().getId()))
                        .collect(Collectors.toList());
            }
        }
        throw LoanNotFoundException.create("User has no loans.");
    }

    public ResponseEntity<String> updateBookReturnDate(Integer loanID, Date bookReturnDate) {
        Optional<Loan> loanOptional = loanRepository.findById(loanID);
        if (loanOptional.isPresent()) {
            Loan loan = loanOptional.get();
            if (bookReturnDate.before(loan.getLoanStartDate())) {
                return new ResponseEntity<>("Book return must be after loan start date.", HttpStatus.BAD_REQUEST);
            }
            loan.setBookReturnDate(bookReturnDate);
            loanRepository.save(loan);
            return new ResponseEntity<>("Book return date: " + bookReturnDate + " ", HttpStatus.OK);
        } else {
            throw LoanNotFoundException.create(loanID);
        }
    }
}
