package com.example.demo.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
@Relation(itemRelation = "loan", collectionRelation = "loans")
public class LoanDTO extends RepresentationModel<LoanDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "User identifier must not be null.")
    @Min(value = 1, message = "User must have a valid identifier.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long userId;

    @JsonIgnoreProperties(value = { "loans" })
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserDTO user;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate loanDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate returnDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate returnForecast;

    @NotNull(message = "ISBN must not be null.")
    @Size(min = 13, max = 13, message = "ISBN must have 13 characters.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String isbn;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BookDTO book;

}
