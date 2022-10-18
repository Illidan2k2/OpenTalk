package com.example.ncc.Service;

import com.example.ncc.dto.role.RoleDto;
import com.example.ncc.entity.Role;
import com.example.ncc.mapper.MapStructMapper;
import com.example.ncc.repository.RoleRepository;
import com.example.ncc.service.RoleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = UserServiceTest.class)
@RunWith(SpringRunner.class)
public class RoleServiceTest {
    @Mock private RoleRepository roleRepository;
    @InjectMocks private RoleService roleService;
    @Mock private Role role;
    @Mock private RoleDto roleDto;
    @Mock private MapStructMapper mapper;

    @Before
    public void setUp(){
        role = new Role();
        role.setId(1);
        role.setName("Admin");
        roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setName(role.getName());
    }

    @Test
    public void addRole(){
        Mockito.when(mapper.roleDto(role)).thenReturn(roleDto);
        Mockito.when(roleService.addRole(roleDto)).thenReturn(role);
        Role savedRole = roleService.addRole(roleDto);
        assertThat(savedRole.getId()).isEqualTo(1);
    }
}
