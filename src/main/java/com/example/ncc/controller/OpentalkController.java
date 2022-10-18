package com.example.ncc.controller;

import com.example.ncc.dto.opentalk.OpentalkContainUserDto;
import com.example.ncc.dto.opentalk.OpentalkDto;
import com.example.ncc.enumeration.Status;
import com.example.ncc.mapper.MapStructMapper;
import com.example.ncc.service.OpentalkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class OpentalkController {
    private final OpentalkService opentalkService;
    private final MapStructMapper mapper;

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/add_opentalk")
    public ResponseEntity<String> addOpentalk(@RequestBody OpentalkDto opentalkDto) {
        opentalkService.addOpentalk(opentalkDto);
        return ResponseEntity.ok().body("Opentalk added successfully!");
    }

    @GetMapping("/presented_opentalk_index")
    public ResponseEntity<Page<OpentalkDto>> viewOpentalkBySelectedDate(@RequestParam(required = false) Integer id,
                                                                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                                        @RequestParam int page,
                                                                        @RequestParam int size) {
        return ResponseEntity.ok().body(opentalkService.getAllOpentalkInSelectedDate(id, startDate, endDate, PageRequest.of(page, size)));
    }

    @GetMapping("/view_user_opentalk/{id}")
    public ResponseEntity<OpentalkContainUserDto> ViewUserInSelectedOpentalk(@PathVariable int id) {
        return ResponseEntity.ok().body(opentalkService.ViewUserInSelectedOpentalk(id));
    }

    @PreAuthorize("hasAuthority('admin')")
    @PutMapping("/update_opentalk")
    public ResponseEntity<String> updateOpentalkIndex(@RequestBody OpentalkDto opentalkDto) {
        opentalkService.updateOpentalkIndex(opentalkDto);
        return ResponseEntity.ok().body("Opentalk edited successfully");
    }

    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("/delete_opentalk/{id}")
    public ResponseEntity<String> deleteOpentalk(@PathVariable int id) {
        opentalkService.deleteOpentalk(id);
        return ResponseEntity.ok().body("Opentalk deleted successfully");
    }

    @GetMapping("/nearest_opentalk")
    public ResponseEntity<OpentalkDto> getNearestOpentalk(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        return ResponseEntity.ok().body(mapper.opentalkDto(opentalkService.getNearestOpentalk(date)));
    }

    @GetMapping("/upcoming_opentalk")
    public ResponseEntity<Page<OpentalkDto>> getUpcomingOpentalk(@RequestParam(required = false) String username,
                                                                 @RequestParam(required = false) String branch,
                                                                 @RequestParam(required = false) Status status,
                                                                 @RequestParam int page,
                                                                 @RequestParam int size) {
        return ResponseEntity.ok().body(opentalkService.getUpcomingOpentalk(branch, username, status, PageRequest.of(page, size)));
    }

    @GetMapping("/previous_opentalk")
    public ResponseEntity<Page<OpentalkDto>> getPreviousOpentalk(@RequestParam(required = false) String username,
                                                                 @RequestParam(required = false) String branch,
                                                                 @RequestParam(required = false) Status status,
                                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                                 @RequestParam int page,
                                                                 @RequestParam int size) {
        return ResponseEntity.ok().body(opentalkService.getPreviousOpentalk(startDate, endDate, branch, username, status, PageRequest.of(page, size)));
    }

}
