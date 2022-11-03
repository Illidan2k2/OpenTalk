package com.example.ncc.dto.role;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
public class RoleDto {
    @JsonProperty
    private int id;

    @JsonProperty
    @NotNull
    private String name;

    public RoleDto(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
