package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.assembler.AuthorAssembler;
import com.example.demo.converter.MapperConverter;
import com.example.demo.dto.AuthorDTO;
import com.example.demo.model.Author;
import com.example.demo.service.AuthorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;
    private final PagedResourcesAssembler<Author> pagedAssembler;
    private final AuthorAssembler authorAssembler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDTO create(@RequestBody @Valid AuthorDTO dto) {
        Author author = MapperConverter.parse(dto, Author.class);
        return this.authorAssembler.toModel(this.authorService.create(author));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDTO update(@PathVariable("id") Long id, @RequestBody @Valid AuthorDTO dto) {
        Author author = MapperConverter.parse(dto, Author.class);
        return this.authorAssembler.toModel(this.authorService.update(id, author));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<AuthorDTO> findAll(Pageable pageable) {
        Page<Author> authors = this.authorService.findAll(pageable);
        return this.pagedAssembler.toModel(authors, this.authorAssembler);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDTO findById(@PathVariable("id") Long id) {
        return this.authorAssembler.toModel(this.authorService.findById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        this.authorService.delete(id);
    }

}
