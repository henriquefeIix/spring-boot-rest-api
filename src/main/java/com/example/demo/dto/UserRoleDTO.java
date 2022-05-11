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
public class UserRoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Role id must not be null.")
    @Min(value = 1, message = "Role must have a valid id.")
    private Long roleId;

}
