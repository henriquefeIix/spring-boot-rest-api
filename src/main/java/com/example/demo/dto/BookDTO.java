package com.example.demo.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.Min;
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
@Relation(itemRelation = "book", collectionRelation = "books")
public class BookDTO extends RepresentationModel<BookDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "Title must not be null.")
    @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters.")
    private String title;

    @NotNull(message = "ISBN must not be null.")
    @Size(min = 10, max = 13, message = "ISBN must be between 10 and 13 characters.")
    private String isbn;

    @NotNull(message = "Publication date must not be null.")
    @Past(message = "Publication date must be less than the current date.")
    private LocalDate publicationDate;

    @NotNull(message = "Number of copies must not be null.")
    @Min(value = 1L, message = "Book must have at least one copy.")
    private Integer numberCopies;

    @Size(max = 500, message = "Synopsis must have a maximum of 500 characters.")
    private String synopsis;

    @JsonIgnoreProperties(value = { "books" })
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<AuthorDTO> authors;

}
