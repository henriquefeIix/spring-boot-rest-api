package com.example.demo.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.demo.controller.BookController;
import com.example.demo.converter.MapperConverter;
import com.example.demo.dto.BookDTO;
import com.example.demo.model.Book;

@Component
public class BookAssembler extends RepresentationModelAssemblerSupport<Book, BookDTO> {

    public BookAssembler() {
        super(BookController.class, BookDTO.class);
    }

    @Override
    public BookDTO toModel(Book entity) {
        BookDTO book = super.createModelWithId(entity.getId(), entity);
        MapperConverter.parse(entity, book);
        book.add(linkTo(methodOn(BookController.class).addAuthor(entity.getId(), null)).withRel("authors"));
        return book;
    }

}
