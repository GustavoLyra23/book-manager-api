package com.bookmanager.bookmanager.controllers;

import com.bookmanager.bookmanager.dto.book.BookRequestDto;
import com.bookmanager.bookmanager.dto.book.BookResponseDto;
import com.bookmanager.bookmanager.services.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_ADMIN')")
    public ResponseEntity<BookResponseDto> addBook(@Valid @ModelAttribute BookRequestDto bookRequestDto) throws IOException {
        var dtoResponse = bookService.insert(bookRequestDto);
        return ResponseEntity.ok().body(dtoResponse);
    }

    @GetMapping(value = "/v1/download/{id}")
    public ResponseEntity<byte[]> retrieveBook(@PathVariable("id") Long id) {
        var bookFile = bookService.findById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + bookFile.getTitle() + "\"")
                .body(bookFile.getFile());
    }

    @GetMapping
    public ResponseEntity<Page<BookResponseDto>> retrieveAllBooks(Pageable pageable) {
        var pagedBooks = bookService.findAllPaged(pageable);
        return ResponseEntity.ok().body(pagedBooks);
    }

    @PutMapping(value = "/v1/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BookResponseDto> update(@Positive(message = "id must be positive") @PathVariable("id") Long id,
                                                  @Valid @ModelAttribute BookRequestDto bookRequestDto) throws IOException {
        var responseDto = bookService.update(bookRequestDto, id);
        return ResponseEntity.ok(responseDto);
    }


}
