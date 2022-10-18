package com.example.ncc.Service;

import com.example.ncc.dto.branch.BranchDto;
import com.example.ncc.dto.role.RoleDto;
import com.example.ncc.dto.user.UserDto;
import com.example.ncc.entity.Branch;
import com.example.ncc.entity.Opentalk;
import com.example.ncc.entity.Role;
import com.example.ncc.entity.User;
import com.example.ncc.enumeration.Status;
import com.example.ncc.mapper.MapStructMapper;
import com.example.ncc.repository.BranchRepository;
import com.example.ncc.repository.OpentalkRepository;
import com.example.ncc.repository.RoleRepository;
import com.example.ncc.repository.UserRepository;
import com.example.ncc.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = UserServiceTest.class)
@RunWith(SpringRunner.class)

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private OpentalkRepository opentalkRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private BranchRepository branchRepository;
    @Mock
    private MapStructMapper mapper;
    @InjectMocks
    private UserService userService;
    @Mock
    private User user;
    @Mock
    private UserDto userDto;
    @Mock
    private Opentalk opentalk;
    @Mock
    private Role role;
    @Mock
    private Branch branch;
    private Status status;
    @Mock
    private RoleDto roleDto;
    @Mock
    private BranchDto branchDto;
    @Mock
    private PasswordEncoder passwordEncoder;
    private LocalDate startDate;
    private LocalDate endDate;

    @Before
    public void setUp() {
        status = Status.ENABLED;
        user = new User();
        user.setId(1);
        user.setUsername("Long Hoang");
        opentalk = new Opentalk();
        opentalk.setId(1);
        userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        role = new Role();
        role.setId(1);
        branch = new Branch();
        branch.setId(1);
        branch.setName("SG1");
        roleDto = new RoleDto();
        roleDto.setId(1);
        branchDto = new BranchDto();
        branchDto.setId(1);
        branchDto.setName("HN2");
        userDto.setRoledto(roleDto);
        userDto.setBranchDTO(branchDto);
        userDto.setStatus(status);
        startDate = LocalDate.of(2022, 10, 12);
        endDate = LocalDate.of(2022, 12, 21);
    }

    @After
    public void tearDown() {
        user = null;
        userDto = null;
        role = null;
        roleDto = null;
        branch = null;
        branchDto = null;
        status = null;
        startDate = endDate = null;
    }

    @Test
    public void addUser() {
        Mockito.when(mapper.userDto(user)).thenReturn(userDto);
        Mockito.when(branchRepository.findById(userDto.getBranchDTO().getId())).thenReturn(Optional.of(branch));
        Mockito.when(roleRepository.findById(userDto.getRoledto().getId())).thenReturn(Optional.of(role));
        Mockito.when(mapper.userDtoToUser(userDto)).thenReturn(user);
        Mockito.when(passwordEncoder.encode(user.getPassword())).thenReturn("12345");
        Mockito.when(userService.addUser(userDto)).thenReturn(user);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void updateUser() {
        Mockito.when(mapper.userDto(user)).thenReturn(userDto);
        Mockito.when(userRepository.findById(userDto.getId())).thenReturn(Optional.of(user));
        user.setUsername("Neko");
        user.setPassword("12345");
        Mockito.when(branchRepository.findById(userDto.getBranchDTO().getId())).thenReturn(Optional.of(branch));
        Mockito.when(roleRepository.findById(userDto.getRoledto().getId())).thenReturn(Optional.of(role));
        Mockito.when(mapper.userDtoToUser(userDto)).thenReturn(user);
        Mockito.when(passwordEncoder.encode(user.getPassword())).thenReturn("12345");
        Mockito.when(userService.updateUser(userDto)).thenReturn(user);
        User updatedUser = userService.updateUser(userDto);
        assertThat(updatedUser.getUsername()).isEqualTo("Neko");
        assertThat(updatedUser.getPassword()).isEqualTo("12345");
    }

    @Test
    public void getAllUserDto() {
        List<User> users = List.of(user);
        Mockito.when(userRepository.getUserByParam("Long Hoang", Status.ENABLED, "HN2"))
                .thenReturn(users);
        Mockito.when(mapper.userDto(user)).thenReturn(userDto);
        List<UserDto> userDtos = List.of(userDto);
        Pageable userDto_pageable = PageRequest.of(0, 5);
        int userDto_start = Math.min((int) userDto_pageable.getOffset(), users.size());
        int userDto_end = Math.min((userDto_start + userDto_pageable.getPageSize()), users.size());
        Page<UserDto> userDTOPage = new PageImpl<>(userDtos);
        doReturn(userDTOPage)
                .when(mock(UserService.class))
                .getAllUserDTO("Long Hoang", Status.ENABLED, "HN2", userDto_pageable);
        assertThat(userService.getAllUserDTO("Long Hoang", Status.ENABLED, "HN2", userDto_pageable)
                .stream()
                .toList())
                .isEqualTo(userDtos);
    }

    @Test
    public void findByIdAndName() {
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userService.findByIdAndName(1, "Long Hoang")).thenReturn(user);
        User existing_user = userService.findByIdAndName(1, "Long Hoang");
        assertThat(existing_user.getId()).isEqualTo(user.getId());
        assertThat(existing_user.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void deleteUser() {
        Optional<User> optional = Optional.of(user);
        Mockito.when(userRepository.findById(1)).thenReturn(optional);
        userService.deleteUser(optional.get().getId());
        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    @WithMockUser(username = "admin", password = "12345", roles = "ADMIN")
    public void joinOpentalk() {
        Optional<Opentalk> optional = Optional.of(opentalk);
        Mockito.when(opentalkRepository.findById(1)).thenReturn(optional);
        assertThat(optional.isPresent()).isTrue();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertThat(authentication).isNotNull();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        assertThat(userDetails).isNotNull();
        Mockito.when(userRepository.findUserByUsername(userDetails.getUsername())).thenReturn(Optional.of(user));
        opentalk.getUsers().add(user);
        Mockito.when(userService.joinOpentalk(1)).thenReturn(opentalk);
        opentalk = userService.joinOpentalk(1);
        assertThat(opentalk.getUsers().contains(user)).isTrue();
    }


}
