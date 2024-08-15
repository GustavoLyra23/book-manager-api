package com.bookmanager.bookmanager.controllers;

import com.bookmanager.bookmanager.dto.BookRequestDto;
import com.bookmanager.bookmanager.dto.BookResponseDto;
import com.bookmanager.bookmanager.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    private BookService bookService;


    @PostMapping
    public ResponseEntity<BookResponseDto> addBook(@Valid @ModelAttribute BookRequestDto bookRequestDto) {

        var dtoResponse = bookService.insert(bookRequestDto);
        return ResponseEntity.ok().body(dtoResponse);
    }


}
