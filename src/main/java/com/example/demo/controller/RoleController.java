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

import com.example.demo.assembler.RoleAssembler;
import com.example.demo.converter.MapperConverter;
import com.example.demo.dto.RoleDTO;
import com.example.demo.model.Role;
import com.example.demo.service.RoleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
    private final PagedResourcesAssembler<Role> pagedAssembler;
    private final RoleAssembler roleAssembler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoleDTO create(@RequestBody @Valid RoleDTO dto) {
        Role role = MapperConverter.parse(dto, Role.class);
        return this.roleAssembler.toModel(this.roleService.create(role));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RoleDTO update(@PathVariable("id") Long id, @RequestBody @Valid RoleDTO dto) {
        Role role = MapperConverter.parse(dto, Role.class);
        return this.roleAssembler.toModel(this.roleService.update(id, role));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<RoleDTO> findAll(Pageable pageable) {
        Page<Role> roles = this.roleService.findAll(pageable);
        return this.pagedAssembler.toModel(roles, this.roleAssembler);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RoleDTO findById(@PathVariable("id") Long id) {
        return this.roleAssembler.toModel(this.roleService.findById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        this.roleService.delete(id);
    }

}
