package com.example.demo.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Relation(itemRelation = "user", collectionRelation = "users")
public class UserDTO extends RepresentationModel<UserDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "First name must not be null.")
    @Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters.")
    private String firstName;

    @NotNull(message = "Last name must not be null.")
    @Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters.")
    private String lastName;

    @NotNull(message = "Birth date must not be null.")
    @Past(message = "Birth date must be less than the current date.")
    private LocalDate birthDate;

    @NotNull(message = "E-mail must not be null.")
    @Email(message = "E-mail must have a valid format.")
    private String email;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<RoleDTO> roles;

    @JsonIgnoreProperties(value = { "user" })
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<LoanDTO> loans;

}
