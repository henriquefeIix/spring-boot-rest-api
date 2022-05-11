package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Book;
import com.example.demo.model.Loan;
import com.example.demo.model.User;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query("SELECT COUNT(l) FROM Loan l JOIN l.book b WHERE b = :book AND l.returnDate IS NULL")
    Integer countLoanedBooks(Book book);

    Page<Loan> findByUser(User user, Pageable pageable);

    @Query("SELECT l FROM Loan l WHERE l.returnDate IS NULL AND l.returnForecast < CURRENT_DATE")
    List<Loan> findOverdueLoans();

}
