package com.example.demo.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Relation(itemRelation = "role", collectionRelation = "roles")
public class RoleDTO extends RepresentationModel<RoleDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "Role must not be null.")
    @Size(min = 3, max = 25, message = "Role must be between 3 and 25 characters.")
    private String role;

    @Size(min = 3, max = 50, message = "Description must be between 3 and 50 characters.")
    private String description;

}
