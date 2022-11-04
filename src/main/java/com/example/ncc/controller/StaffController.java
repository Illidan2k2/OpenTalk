package com.example.ncc.controller;

import com.example.ncc.dto.Staff.StaffDto;
import com.example.ncc.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class StaffController {
    private final StaffService staffService;

    @PostMapping("/add_staff")
    public ResponseEntity<String> addStaff(@RequestBody StaffDto staffDto){
        staffService.addStaff(staffDto);
        return ResponseEntity.ok().body("Staff added successfully!");
    }

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

    @PutMapping("/update_staff")
    public ResponseEntity<String> updateStaff(@RequestBody StaffDto staffDto){
        staffService.updateStaff(staffDto);
        return ResponseEntity.ok().body("Staff updated successfully");
    }

    @DeleteMapping("/remove_staff/{id}")
    public ResponseEntity<String> removeStaff(@PathVariable int id){
        staffService.removeStaff(id);
        return ResponseEntity.ok().body("Staff removed successfully");
    }
}
