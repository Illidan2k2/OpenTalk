package com.example.ncc.dto.user;

import com.example.ncc.dto.branch.BranchDto;
import com.example.ncc.dto.role.RoleDto;
import com.example.ncc.enumeration.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserDto {
    @JsonProperty
    private int id;

    @JsonProperty
    @NotNull
    @Size(min = 5, max = 30, message = "The name must be between 5 and 30 characters")
    private String username;

    @JsonProperty
    private Status status;

    @NotNull
    @JsonProperty
    private String phone_number;

    @NotNull
    @JsonProperty
    private String address;

    @NotNull
    @Email(message = "Email must be valid")
    @JsonProperty
    private String email;

    @JsonProperty
    private RoleDto roledto;

    @JsonProperty
    private BranchDto branchDTO;

    @NotNull
    @JsonProperty
    private String password;



}
