package com.example.ncc.dto.opentalk;

import com.example.ncc.dto.branch.BranchDto;
import com.example.ncc.dto.user.UserDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OpentalkDto {
    @JsonProperty
    private int id;

    @JsonProperty
    private String topic;

    @JsonProperty
    private LocalDateTime date;

    @JsonProperty
    private String link;

    @JsonProperty
    private BranchDto branchDTO;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<UserDto> userDtos;


}
