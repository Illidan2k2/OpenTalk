package com.example.ncc.dto.user;

import com.example.ncc.dto.branch.BranchDto;
import com.example.ncc.dto.opentalk.OpentalkDto;
import com.example.ncc.enumeration.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOpentalkDto {
    @JsonProperty
    private int id;

    @JsonProperty
    private String username;

    @JsonProperty
    private BranchDto branchDto;

    @JsonProperty
    private Status status;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<OpentalkDto> opentalkDtos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserOpentalkDto that)) return false;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
