package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role create(Role role) {
        if (this.roleRepository.findByRole(role.getRole()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role with the given name already exists.");
        }

        return this.roleRepository.save(role);
    }

    public Role update(Long id, Role role) {
        Role entity = this.findById(id);

        if (!role.getRole().equals(entity.getRole()) && this.roleRepository.findByRole(role.getRole()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role with the given name already exists.");
        }

        entity.setRole(role.getRole());
        entity.setDescription(role.getDescription());

        return this.roleRepository.save(entity);
    }

    public Page<Role> findAll(Pageable pageable) {
        return this.roleRepository.findAll(pageable);
    }

    public Role findById(Long id) {
        return this.roleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role with the given id not found."));
    }

    public Role findByRole(String role) {
        return this.roleRepository.findByRole(role)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role with the given name not found."));
    }

    public void delete(Long id) {
        this.roleRepository.delete(this.findById(id));
    }

}
