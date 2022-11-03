package com.example.ncc.Repository;

import com.example.ncc.entity.Branch;
import com.example.ncc.entity.Opentalk;
import com.example.ncc.entity.Role;
import com.example.ncc.entity.User;
import com.example.ncc.enumeration.Status;
import com.example.ncc.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {UserRepository.class})
@SpringBootTest
public class UserRepositoryTest {
    @Mock
    private UserRepository userRepository;
    private User user;
    private Opentalk opentalk;
    private Role role;
    private Branch branch;
    private List<User> users;
    private Status status;
    private LocalDate startDate;
    private LocalDate endDate;
    @Before
    public void setUp(){
        user = new User();
        user.setId(1);
        user.setUsername("Long");
        users = List.of(user);
        role = new Role();
        role.setId(1);
        role.setName("HR");
        user.setRole(role);
        branch = new Branch();
        branch.setId(1);
        branch.setName("HN1");
        user.setBranch(branch);
        status = Status.ENABLED;
        user.setStatus(status);
        startDate = LocalDate.of(2022,10,12);
        endDate = LocalDate.of(2022,12,21);
        opentalk = new Opentalk();
        opentalk.setDate(LocalDate.of(2022,11,12).atStartOfDay());
        user.setOpentalks(Set.of(opentalk));
    }

    @After
    public void tearDown(){
        user = null;
        role = null;
        branch = null;
        users = null;
        startDate = null;
        endDate = null;
    }

    @Test
    public void findUserByIdAndName(){
        Mockito.when(userRepository.findByIdAndName(1,"Long")).thenReturn(user);
        User savedUser = userRepository.findByIdAndName(1,"Long");
        assertEquals(savedUser,user);
    }

    @Test
    public void findUserByUserName(){
        Optional<User> optional = Optional.of(user);
        Mockito.when(userRepository.findUserByUsername("Long")).thenReturn(optional);
        assertEquals(1,userRepository.findUserByUsername("Long").get().getId());
    }

    @Test
    public void findByRole(){
        Pageable pageable = PageRequest.of(0,5);
        int start = Math.min((int)pageable.getOffset(), users.size());
        int end = Math.min((start + pageable.getPageSize()), users.size());
        Page userPage = new PageImpl(users.subList(start,end),pageable,users.size());
        Mockito.when(userRepository.findByRole(role,pageable)).thenReturn(userPage);
        assertThat(userRepository.findByRole(role,pageable).stream().toList()).isEqualTo(users);
    }

    @Test
    public void getUserByParam(){
        Mockito.when(userRepository.getUserByParam("Long Hoang",Status.ENABLED,"HN1"))
                .thenReturn(users);
        assertThat(userRepository.getUserByParam("Long Hoang",Status.ENABLED,"HN1"))
                .asList().contains(user);
    }

    @Test
    public void getUnregisteredUser(){
        //startDate = LocalDate.of(2022,10,12);
        //endDate = LocalDate.of(2022,12,21);
        if(opentalk.getDate().isBefore(endDate.atStartOfDay()) && opentalk.getDate().isAfter(startDate.atStartOfDay())){
            user.setOpentalks(null);
        }
        Mockito.when(userRepository.getEnabledUser("Long Hoang","HN1",startDate,endDate))
                .thenReturn(users);
        assertThat(userRepository.getEnabledUser("Long Hoang","HN1",startDate,endDate))
                .asList().contains(user);
        assertThat(user.getOpentalks()==null);
    }
}
