package com.example.demo.assembler;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.demo.controller.RoleController;
import com.example.demo.converter.MapperConverter;
import com.example.demo.dto.RoleDTO;
import com.example.demo.model.Role;

@Component
public class RoleAssembler extends RepresentationModelAssemblerSupport<Role, RoleDTO> {

    public RoleAssembler() {
        super(RoleController.class, RoleDTO.class);
    }

    @Override
    public RoleDTO toModel(Role entity) {
        RoleDTO role = super.createModelWithId(entity.getId(), entity);
        MapperConverter.parse(entity, role);
        return role;
    }

}
