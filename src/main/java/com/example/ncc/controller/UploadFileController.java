package com.example.ncc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;

@Controller
@RequestMapping
public class UploadFileController {
    Logger log = LoggerFactory.getLogger(this.getClass());
    //@PreAuthorize("hasAuthority('employee') and hasAuthority('admin')")
    @PostMapping(value = "/upload_file",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE} )
    public ResponseEntity<String> uploadSingleFile(@RequestParam MultipartFile file) throws IOException {
        log.info("Request contains, File: " + file.getOriginalFilename());
        file.transferTo(Paths.get("C:\\Users\\Acer\\Java Spring interns\\NCC\\src\\main\\java\\com\\example\\ncc\\upload_files\\" + file.getOriginalFilename()));
        return ResponseEntity.ok("Upload successfully!");
    }

}
