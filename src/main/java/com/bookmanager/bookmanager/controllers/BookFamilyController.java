package com.bookmanager.bookmanager.controllers;

import com.bookmanager.bookmanager.dto.bookfamiliy.BookFamilyDto;
import com.bookmanager.bookmanager.dto.bookfamiliy.BookFamilyRequestDto;
import com.bookmanager.bookmanager.services.BookFamilyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "families")
public class BookFamilyController {

    @Autowired
    private BookFamilyService bookFamilyService;


    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_ADMIN')")
    public ResponseEntity<Page<BookFamilyDto>> getAllBookFamilies(Pageable pageable) {
        var page = bookFamilyService.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_ADMIN')")
    public ResponseEntity<BookFamilyDto> insert(@Valid @RequestBody BookFamilyRequestDto bookFamilyRequestDto) {
        var bookFamilyDto = bookFamilyService.insert(bookFamilyRequestDto);
        return ResponseEntity.ok(bookFamilyDto);
    }

    @PutMapping(value = "/v1/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BookFamilyDto> update(@PathVariable("id") Long id, @Valid @RequestBody BookFamilyRequestDto bookFamilyRequestDto) {
        BookFamilyDto bookFamilyDto = bookFamilyService.update(id, bookFamilyRequestDto);

        return ResponseEntity.ok(bookFamilyDto);
    }

}
