package com.example.ncc.Controller;

import com.example.ncc.controller.UserController;
import com.example.ncc.dto.opentalk.OpentalkDto;
import com.example.ncc.dto.user.UserDto;
import com.example.ncc.dto.user.UserOpentalkDto;
import com.example.ncc.entity.Branch;
import com.example.ncc.entity.Opentalk;
import com.example.ncc.entity.User;
import com.example.ncc.enumeration.Status;
import com.example.ncc.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@RunWith(SpringRunner.class)
@Import({UserController.class})
@ContextConfiguration(classes = {UserController.class})
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
    @MockBean
    UserService userService;

    @MockBean
    PasswordEncoder passwordEncoder;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserController userController;
    @Mock private User user;
    @Mock private UserDto userDto;
    @Mock private Opentalk opentalk;
    @Mock private UserOpentalkDto userOpentalkDto;
    @Mock private OpentalkDto opentalkDto;
    private LocalDate startDate;
    private LocalDate endDate;
    private Branch branch;
    private Status status;
    @Before
    public void setUp(){
        user = new User();
        user.setId(3);
        user.setUsername("Long Dep Trai");
        userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        opentalk = new Opentalk();
        opentalk.setId(1);
        userOpentalkDto = new UserOpentalkDto();
        userOpentalkDto.setId(1);
        startDate = LocalDate.of(2022, 11, 12);
        endDate = LocalDate.of(2022, 12, 31);
        opentalkDto = new OpentalkDto();
        userOpentalkDto.setOpentalkDtos(Set.of(opentalkDto));
        branch = new Branch();
        branch.setId(1);
        branch.setName("HN3");
        status = Status.ENABLED;
    }

    @After
    public void tearDown(){
        branch = null;
        opentalkDto = null;
        userOpentalkDto = null;
        opentalk = null;
        user = null;
        userDto = null;
    }

    @Test
    @WithMockUser(username = "admin", password = "12345", roles = "ADMIN")
    public void testFindUser() throws Exception {
        Mockito.when(userService.findByIdAndName(3, "Long Dep Trai").thenReturn(user);
        mockMvc.perform(get("/find_user")
                        .param("id", String.valueOf(3))
                        .param("name", "Long Dep Trai"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(3)))
                .andExpect(jsonPath("username",is("Long Dep Trai")));
    }

    @Test
    public void addUser() throws Exception {
        String json_add = "{\"username\":\"HuyHoang\",\"email\":\"huyhuyhoangb45@gmail.com\",\"roledto\":{\"id\":2},\"branchDTO\":{\"id\":4},\"password\":\"54321\",\"status\":\"DISABLED\",\"phone_number\":\"01663403287\",\"address\":\"74Qixing\"}";
        Mockito.when(userService.addUser(userDto)).thenReturn(user);
        mockMvc.perform(post("/add_user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json_add))
                .andExpect(status().isOk())
                .andReturn().getResponse();
    }

    @Test
    public void getAllUserDto() throws Exception {
        Page<UserDto> userDtos_page = new PageImpl<>(List.of(userDto));
        int page = 0, size = 5;
        Pageable pageable = PageRequest.of(page,size);
        Mockito.when(userService.getAllUserDTO("Long Hoang",Status.ENABLED,"HN3",pageable)).thenReturn(userDtos_page);
        mockMvc.perform(get("/userDTO_index")
                        .param("size",String.valueOf(size))
                        .param("page",String.valueOf(page))
                        .param("branch","HN3")
                        .param("status","ENABLED"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateUser() throws Exception {
        String json_update = "{\"id\":3,\"username\":\"Kaneki\",\"email\":\"longphgbh200168@fpt.edu.vn\",\"roledto\":{\"id\":2},\"branchDTO\":{\"id\":3},\"password\":\"54321\",\"status\":\"DISABLED\",\"phone_number\":\"01663403287\",\"address\":\"74Qixing\"}";
        Mockito.when(userService.updateUser(userDto)).thenReturn(user);
        mockMvc.perform(put("/update_user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json_update))
                .andExpect(status().isOk())
                .andReturn().getResponse();
    }

    @Test
    public void deleteUser() throws Exception {
        Mockito.when(userService.findByIdAndName(3, "Long Dep Trai")).thenReturn(user);
        mockMvc.perform(delete("/delete_user/{id}"))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        assertThat(user).isNull();
    }

    @Test
    public void joinOpentalk() throws Exception {
        String url = "http://localhost:8080/join_opentalk/1";
        Mockito.when(userService.joinOpentalk(1)).thenReturn(opentalk);
        mockMvc.perform(post(url))
                .andExpect(status().isOk())
                .andReturn().getResponse();
    }



}
