package com.example.demo.assembler;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.demo.controller.AuthorController;
import com.example.demo.converter.MapperConverter;
import com.example.demo.dto.AuthorDTO;
import com.example.demo.model.Author;

@Component
public class AuthorAssembler extends RepresentationModelAssemblerSupport<Author, AuthorDTO> {

    public AuthorAssembler() {
        super(AuthorController.class, AuthorDTO.class);
    }

    @Override
    public AuthorDTO toModel(Author entity) {
        AuthorDTO author = super.createModelWithId(entity.getId(), entity);
        MapperConverter.parse(entity, author);
        return author;
    }

}
