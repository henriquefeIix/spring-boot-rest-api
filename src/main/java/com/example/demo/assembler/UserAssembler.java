package com.example.demo.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.demo.controller.UserController;
import com.example.demo.converter.MapperConverter;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;

@Component
public class UserAssembler extends RepresentationModelAssemblerSupport<User, UserDTO> {

    public UserAssembler() {
        super(UserController.class, UserDTO.class);
    }

    @Override
    public UserDTO toModel(User entity) {
        UserDTO user = super.createModelWithId(entity.getId(), entity);
        MapperConverter.parse(entity, user);
        user.add(linkTo(methodOn(UserController.class).addRole(entity.getId(), null)).withRel("roles"));
        return user;
    }

}
