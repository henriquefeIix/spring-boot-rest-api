package com.example.demo.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

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
@Relation(itemRelation = "author", collectionRelation = "authors")
public class AuthorDTO extends RepresentationModel<AuthorDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "Name must not be null.")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters.")
    private String name;

    @NotNull(message = "Birth date must not be null.")
    @Past(message = "Birth date must be less than the current date.")
    private LocalDate birthDate;

    @JsonIgnoreProperties(value = { "authors" })
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<BookDTO> books;

}
