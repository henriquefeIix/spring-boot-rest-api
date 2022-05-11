package com.example.demo.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanReturnDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Return status must not be null.")
    private Boolean returned;

}
