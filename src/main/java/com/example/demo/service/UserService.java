package com.example.demo.service;

import java.util.Collections;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.UserRoleDTO;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final RoleService roleService;
    private final UserRepository userRepository;
    
    public User create(User user) {
        if (this.userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with the given e-mail already exists.");
        }
        
        user.setRoles(Collections.emptyList());
        user.setLoans(Collections.emptyList());

        return this.userRepository.save(user);
    }
    
    public User update(Long id, User user) {
        User entity = this.findById(id);

        if (!user.getEmail().equals(entity.getEmail())
                && this.userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with the given e-mail already exists.");
        }

        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setBirthDate(user.getBirthDate());
        entity.setEmail(user.getEmail());

        return this.userRepository.save(entity);
    }
    
    public Page<User> findAll(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    public User findById(Long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with the given id not found."));
    }

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with the given email not found."));
    }
    
    public void delete(Long id) {
        this.userRepository.delete(this.findById(id));
    }
    
    public User addRole(Long id, UserRoleDTO userRole) {
        User user = this.findById(id);
        Role foundRole = this.roleService.findById(userRole.getRoleId());

        if (user.getRoles().stream().anyMatch(r -> r.equals(foundRole))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The given role already exists among the user roles.");
        }

        user.getRoles().add(foundRole);
        return this.userRepository.save(user);
    }

}
