package com.example.demo.service;

import java.util.Collections;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.model.Author;
import com.example.demo.repository.AuthorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public Author create(Author author) {
        author.setBooks(Collections.emptyList());
        return this.authorRepository.save(author);
    }

    public Author update(Long id, Author author) {
        Author entity = this.findById(id);
        entity.setName(author.getName());
        entity.setBirthDate(author.getBirthDate());

        return this.authorRepository.save(entity);
    }

    public Page<Author> findAll(Pageable pageable) {
        return this.authorRepository.findAll(pageable);
    }

    public Author findById(Long id) {
        return this.authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author with the given id not found."));
    }

    public void delete(Long id) {
        this.authorRepository.delete(this.findById(id));
    }

}
