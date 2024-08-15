package com.bookmanager.bookmanager.controllers;

import com.bookmanager.bookmanager.dto.bookfamiliy.BookFamilyDto;
import com.bookmanager.bookmanager.services.BookFamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "families")
public class BookFamilyController {

    @Autowired
    private BookFamilyService bookFamilyService;


    @GetMapping
    public ResponseEntity<Page<BookFamilyDto>> getAllBookFamilies(Pageable pageable) {
        var page = bookFamilyService.findAll(pageable);
        return ResponseEntity.ok(page);
    }
}
