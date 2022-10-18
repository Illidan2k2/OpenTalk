package com.example.ncc.dto.opentalk;

import com.example.ncc.dto.user.UserDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class OpentalkContainUserDto {
    @JsonProperty
    private int id;

    @JsonProperty
    private String topic;

    @JsonProperty
    private Set<UserDto> userDtos;
}
