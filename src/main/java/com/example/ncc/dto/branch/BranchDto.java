package com.example.ncc.dto.branch;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BranchDto {
    @JsonProperty
    private int id;

    @JsonProperty
    @NotNull
    private String name;
}
