package com.example.demo.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.BookAuthorDTO;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    private final AuthorService authorService;
    private final BookRepository bookRepository;

    public Book create(Book book) {
        if (this.bookRepository.findByIsbn(book.getIsbn()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book with the given ISBN already exists.");
        }

        return this.bookRepository.save(book);
    }

    public Book update(Long id, Book book) {
        Book entity = this.findById(id);

        if (!book.getIsbn().equals(entity.getIsbn()) && this.bookRepository.findByIsbn(book.getIsbn()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book with the given ISBN already exists.");
        }

        entity.setTitle(book.getTitle());
        entity.setPublicationDate(book.getPublicationDate());
        entity.setSynopsis(book.getSynopsis());

        return this.bookRepository.save(entity);
    }

    public Page<Book> find(Book book, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase()
                .withIgnoreNullValues().withStringMatcher(StringMatcher.CONTAINING);

        return this.bookRepository.findAll(Example.of(book, matcher), pageable);
    }

    public Book findById(Long id) {
        return this.bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with the given id not found."));
    }

    public Book findByIsbn(String isbn) {
        return this.bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with the given ISBN not found."));
    }

    public void delete(Long id) {
        this.bookRepository.delete(this.findById(id));
    }

    public Book addAuthor(Long id, BookAuthorDTO author) {
        Book book = this.findById(id);
        Author foundAuthor = this.authorService.findById(author.getAuthorId());

        if (book.getAuthors().stream().anyMatch(a -> a.equals(foundAuthor))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The given author already exists among the book authors.");
        }

        book.getAuthors().add(foundAuthor);
        return this.bookRepository.save(book);
    }

}
