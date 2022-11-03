package com.example.ncc.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserSenderDto {
    @JsonProperty
    private int id;

    @JsonProperty
    private String username;

    @JsonProperty
    private UserSenderDto senderDto;
}
