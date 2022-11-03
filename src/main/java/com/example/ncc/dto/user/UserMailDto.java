package com.example.ncc.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserMailDto {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int id;

    @JsonProperty
    private String username;

    @JsonProperty
    private String email;
}
