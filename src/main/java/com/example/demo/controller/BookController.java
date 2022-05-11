package com.example.demo.controller;

import java.util.Collections;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.assembler.BookAssembler;
import com.example.demo.converter.MapperConverter;
import com.example.demo.dto.BookAuthorDTO;
import com.example.demo.dto.BookDTO;
import com.example.demo.dto.BookFilterDTO;
import com.example.demo.model.Book;
import com.example.demo.service.BookService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final PagedResourcesAssembler<Book> pagedAssembler;
    private final BookAssembler bookAssembler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@RequestBody @Valid BookDTO dto) {
        Book book = MapperConverter.parse(dto, Book.class);
        book.setAuthors(Collections.emptyList());
        return this.bookAssembler.toModel(this.bookService.create(book));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookDTO update(@PathVariable("id") Long id, @RequestBody @Valid BookDTO dto) {
        Book book = MapperConverter.parse(dto, Book.class);
        return this.bookAssembler.toModel(this.bookService.update(id, book));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<BookDTO> find(BookFilterDTO dto, Pageable pageable) {
        Book book = MapperConverter.parse(dto, Book.class);
        Page<Book> books = this.bookService.find(book, pageable);
        return this.pagedAssembler.toModel(books, this.bookAssembler);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookDTO findById(@PathVariable("id") Long id) {
        return this.bookAssembler.toModel(this.bookService.findById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        this.bookService.delete(id);
    }

    @PatchMapping("/{id}/authors")
    @ResponseStatus(HttpStatus.OK)
    public BookDTO addAuthor(@PathVariable("id") Long id, @RequestBody @Valid BookAuthorDTO dto) {
        return this.bookAssembler.toModel(this.bookService.addAuthor(id, dto));
    }

}
