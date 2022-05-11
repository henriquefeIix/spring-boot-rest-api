package com.example.demo.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookAuthorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Author id must not be null.")
    @Min(value = 1, message = "Author must have a valid id.")
    private Long authorId;

}
