package com.example.ncc.controller;

import com.example.ncc.Exception.BranchNotFoundException;
import com.example.ncc.dto.branch.BranchDto;
import com.example.ncc.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class BranchController {
    private final BranchService branchService;

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/add_branch")
    public ResponseEntity<String> addBranch(@RequestBody BranchDto branchDto) {
        branchService.addBranch(branchDto);
        return ResponseEntity.ok().body("Branch added successfully!");
    }

    @GetMapping("/view_branch")
    public ResponseEntity<Page<BranchDto>> viewBranch(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok().body(branchService.viewAllBranchDTO(PageRequest.of(page,size)));
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/update_branch")
    public ResponseEntity<String> updateBranch(@RequestBody BranchDto branchDto) throws BranchNotFoundException {
        branchService.updateBranch(branchDto);
        return ResponseEntity.ok().body("Branch edited successfully");
    }

    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("/delete_branch/{id}")
    public ResponseEntity<String> deleteBranch(@PathVariable int id) {
        branchService.removeBranch(id);
        return ResponseEntity.ok().body("Branch deleted successfully");
    }

    /*@PostConstruct
    public void Inform(){
        System.out.println("Generate bean");
    }

    @PreDestroy
    public void Delete(){
        System.out.println("Delete bean");
    }*/
}
