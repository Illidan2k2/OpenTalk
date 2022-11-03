package com.example.ncc.controller;

import com.example.ncc.dto.Staff.StaffDto;
import com.example.ncc.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class StaffController {
    private final StaffService staffService;

    @GetMapping("/get_staffs")
    public ResponseEntity<Page<StaffDto>> getStaffByParam(@RequestParam(required = false) String firstName,
                                                          @RequestParam(required = false) String lastName,
                                                          @RequestParam int page,
                                                          @RequestParam int size){
        return ResponseEntity.ok().body(staffService.getStaffByParam(firstName,lastName, PageRequest.of(page,size)));
    }

    @GetMapping("/save_public_staffs")
    public ResponseEntity<String> savePublicStaff(){
        staffService.savePublicStaffs();
        return ResponseEntity.ok().body("Save staffs successfully!");
    }
}
