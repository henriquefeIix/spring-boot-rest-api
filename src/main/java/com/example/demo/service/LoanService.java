package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.LoanReturnDTO;
import com.example.demo.model.Book;
import com.example.demo.model.Loan;
import com.example.demo.model.User;
import com.example.demo.repository.LoanRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final BookService bookService;
    private final LoanRepository loanRepository;
    private final UserService userService;

    public Loan create(Loan loan, Long userId, String isbn) {
        Book book = this.bookService.findByIsbn(isbn);
        User user = this.userService.findById(userId);

        if (this.loanRepository.countLoanedBooks(book) >= book.getNumberCopies()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There are no copies of this book available.");
        }

        loan.setBook(book);
        loan.setLoanDate(LocalDate.now());
        loan.setReturnForecast(LocalDate.now().plusDays(7));
        loan.setUser(user);

        return this.loanRepository.save(loan);
    }

    public Loan update(Long id, LoanReturnDTO loanReturn) {
        Loan loan = this.findById(id);
        loan.setReturnDate(loanReturn.getReturned() ? LocalDate.now() : null);
        return this.loanRepository.save(loan);
    }

    public Page<Loan> findAll(Pageable pageable) {
        return this.loanRepository.findAll(pageable);
    }

    public Loan findById(Long id) {
        return this.loanRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan with the given id not found."));
    }

    public void delete(Long id) {
        this.loanRepository.delete(this.findById(id));
    }

    public Page<Loan> findByUser(User user, Pageable pageable) {
        return this.loanRepository.findByUser(user, pageable);
    }

    public List<Loan> findOverdueLoans() {
        return this.loanRepository.findOverdueLoans();
    }

}
