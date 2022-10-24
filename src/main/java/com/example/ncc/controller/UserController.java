package com.example.ncc.controller;

import com.example.ncc.dto.opentalk.OpentalkDto;
import com.example.ncc.dto.user.UserDto;
import com.example.ncc.dto.user.UserOpentalkDto;
import com.example.ncc.entity.User;
import com.example.ncc.enumeration.Status;
import com.example.ncc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/add_user")
    public ResponseEntity<String> addUser(@Valid @RequestBody UserDto userDto) {
        userService.addUser(userDto);
        return ResponseEntity.ok().body("Create new user successfully!");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        userService.addUser(userDto);
        return ResponseEntity.ok().body("Sign in successfully!");
    }

    @GetMapping("/userDTO_index")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Page<UserDto>> getAllUserDTO(@RequestParam(required = false) String username,
                                       @RequestParam(required = false) Status status,
                                       @RequestParam(required = false) String branch,
                                       @RequestParam int page,
                                       @RequestParam int size) {
        return ResponseEntity.ok().body(userService.getAllUserDTO(username, status, branch, PageRequest.of(page, size)));
    }

    @GetMapping("/opentalk_joined_user")
    public Page<OpentalkDto> viewJoinedOpentalkByUser(@RequestParam Integer id,
                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                      @RequestParam int page,
                                                      @RequestParam int size) {
        return userService.viewJoinedOpentalkByUser(id, startDate, endDate, PageRequest.of(page, size));
    }

    @PreAuthorize("hasAuthority('admin')")
    @PutMapping("/update_user")
    public ResponseEntity<String> updateUser(@RequestBody UserDto userDto) {
        userService.updateUser(userDto);
        return ResponseEntity.ok().body("User updated successfully!");
    }

    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("/delete_user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().body("User deleted successfully!");
    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/find_user")
    public ResponseEntity<User> findUser(@RequestParam int id, @RequestParam(name = "name") String username) {
        return ResponseEntity.ok().body(userService.findByIdAndName(id, username));
    }

    @PostMapping("/join_opentalk/{id}")
    public ResponseEntity<String> joinOpentalk(@PathVariable int id) {
        userService.joinOpentalk(id);
        return ResponseEntity.ok().body("You had joined this opentalk!");
    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/view_unregistered_user")
    public ResponseEntity<Page<UserOpentalkDto>> viewUnregisteredUser(@RequestParam String username,
                                                      @RequestParam String branch,
                                                      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                      @RequestParam int page,
                                                      @RequestParam int size) {
        return ResponseEntity.ok().body(userService.getUnregisteredUser(username, branch, startDate, endDate, PageRequest.of(page, size)));
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/dial_opentalk")
    public ResponseEntity<UserOpentalkDto> opentalkRandomDial(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        UserOpentalkDto userOpentalkDto = userService.opentalkRandomDial(startDate,endDate);
        return ResponseEntity.ok().body(userOpentalkDto);
    }

}
