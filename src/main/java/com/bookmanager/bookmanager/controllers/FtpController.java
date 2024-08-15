package com.bookmanager.bookmanager.controllers;

import com.bookmanager.bookmanager.services.FtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/ftp")
public class FtpController {

    @Autowired
    private FtpService ftpService;

    @PostMapping(value = "/v1/{format}")
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_ADMIN')")
    public ResponseEntity<Void> sendToFtpServer(@RequestParam(value = "files") List<MultipartFile> files, @PathVariable("format") String format) throws IOException {
        ftpService.sendFileToFtpServer(files, format);
        return ResponseEntity.noContent().build();
    }


}
