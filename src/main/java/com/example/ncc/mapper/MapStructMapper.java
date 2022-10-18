package com.example.ncc.mapper;

import com.example.ncc.dto.branch.BranchDto;
import com.example.ncc.dto.opentalk.OpentalkContainUserDto;
import com.example.ncc.dto.opentalk.OpentalkDto;
import com.example.ncc.dto.role.RoleDto;
import com.example.ncc.dto.user.UserDto;
import com.example.ncc.dto.user.UserOpentalkDto;
import com.example.ncc.entity.Branch;
import com.example.ncc.entity.Opentalk;
import com.example.ncc.entity.Role;
import com.example.ncc.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface MapStructMapper {
    BranchDto branchDto(Branch branch);
    @Mapping(target = "id", ignore = true)
    Branch branchDtoToBranch(BranchDto branchDto);
    @Mapping(target = "branchDTO", expression = "java(branchDto(opentalk.getBranch()))")
    @Mapping(target = "userDtos", expression = "java(opentalk.getUsers().stream().map(user -> userDto(user)).collect(java.util.stream.Collectors.toSet()))")
    OpentalkDto opentalkDto (Opentalk opentalk);

    RoleDto roleDto (Role role);

    @Mapping(target = "id", ignore = true)
    Role roleDtoToRole(RoleDto roleDto);

    @Mapping(target = "roledto", expression = "java(roleDto(user.getRole()))")
    @Mapping(target = "branchDTO", expression = "java(branchDto(user.getBranch()))")
    UserDto userDto (User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", expression = "java(roleDtoToRole(userDto.getRoledto()))")
    @Mapping(target = "branch", expression = "java(branchDtoToBranch(userDto.getBranchDTO()))")
    User userDtoToUser(UserDto userDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", expression = "java(opentalkDto.getUserDtos().stream().map(userDto -> userDtoToUser(userDto)).collect(java.util.stream.Collectors.toSet()))")
    Opentalk opentalkDtoToOpenTalk(OpentalkDto opentalkDto);

    @Mapping(target = "branchDto", expression = "java(branchDto(user.getBranch()))")
    @Mapping(target = "opentalkDtos", expression = "java(user.getOpentalks().stream().map(opentalk -> opentalkDto(opentalk)).collect(java.util.stream.Collectors.toSet()))")
    UserOpentalkDto userOpentalkDto (User user);

    @Mapping(target = "userDtos", expression = "java(opentalk.getUsers().stream().map(user -> userDto(user)).collect(java.util.stream.Collectors.toSet()))")
    OpentalkContainUserDto opentalkContainUserDto(Opentalk opentalk);


}
