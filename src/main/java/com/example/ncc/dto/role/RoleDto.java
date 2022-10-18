package com.example.ncc.dto.role;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class RoleDto {
    @JsonProperty
    private int id;

    @JsonProperty
    @NotNull
    private String name;
}
