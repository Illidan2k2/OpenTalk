package com.example.ncc.controller;

import com.example.ncc.dto.Mail.ReceivedMailDto;
import com.example.ncc.dto.Mail.SentMailDto;
import com.example.ncc.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/mail")
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;

    @PostMapping("/send_message")
    public ResponseEntity<String> sendMessage(@RequestBody SentMailDto sentMailDto) {
        mailService.sendMessage(sentMailDto);
        return ResponseEntity.ok().body("Message sent successfully!");
    }

    @GetMapping("/sent_mail")
    public ResponseEntity<Page<SentMailDto>> viewSentMail(@RequestParam(required = false) String title,
                                                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                                          @RequestParam int page,
                                                          @RequestParam int size) {
        return ResponseEntity.ok().body(mailService.viewSentMail(title, startDate, endDate, PageRequest.of(page, size)));
    }

    @GetMapping("/received_mail")
    public ResponseEntity<Page<ReceivedMailDto>> viewReceivedMail(@RequestParam(required = false) String title,
                                                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                                                  @RequestParam int page,
                                                                  @RequestParam int size) {
        return ResponseEntity.ok().body(mailService.viewReceivedMail(title, startDate, endDate, PageRequest.of(page, size)));
    }

}
