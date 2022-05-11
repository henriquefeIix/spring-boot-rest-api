package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
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

import com.example.demo.assembler.UserAssembler;
import com.example.demo.converter.MapperConverter;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserRoleDTO;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PagedResourcesAssembler<User> pagedAssembler;
    private final UserAssembler userAssembler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@RequestBody @Valid UserDTO dto) {
        User user = MapperConverter.parse(dto, User.class);
        return this.userAssembler.toModel(this.userService.create(user));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO update(@PathVariable("id") Long id, @RequestBody @Valid UserDTO dto) {
        User user = MapperConverter.parse(dto, User.class);
        return this.userAssembler.toModel(this.userService.update(id, user));
    }

    @GetMapping
    public PagedModel<UserDTO> findAll(Pageable pageable) {
        Page<User> users = this.userService.findAll(pageable);
        return this.pagedAssembler.toModel(users, this.userAssembler);
    }

    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable Long id) {
        return this.userAssembler.toModel(this.userService.findById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        this.userService.delete(id);
    }

    @PutMapping("/{id}/roles")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO addRole(@PathVariable("id") Long id, @RequestBody @Valid UserRoleDTO dto) {
        return this.userAssembler.toModel(this.userService.addRole(id, dto));
    }

}
