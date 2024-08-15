package com.bookmanager.bookmanager.controllers;

import com.bookmanager.bookmanager.dto.book.BookRequestDto;
import com.bookmanager.bookmanager.dto.book.BookResponseDto;
import com.bookmanager.bookmanager.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    private BookService bookService;


    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_ADMIN')")
    public ResponseEntity<BookResponseDto> addBook(@Valid @ModelAttribute BookRequestDto bookRequestDto) {
        var dtoResponse = bookService.insert(bookRequestDto);
        return ResponseEntity.ok().body(dtoResponse);
    }

    @GetMapping(value = "/v1/{id}")
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_ADMIN')")
    public ResponseEntity<byte[]> retrieveBook(@PathVariable("id") Long id) {
        var bookFile = bookService.findById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + bookFile.getTitle() + "\"")
                .body(bookFile.getFile());

    }


}
