package com.bookmanager.bookmanager.services;

import com.bookmanager.bookmanager.dto.BookRequestDto;
import com.bookmanager.bookmanager.dto.BookResponseDto;
import com.bookmanager.bookmanager.entities.Book;
import com.bookmanager.bookmanager.repositories.BookFamilyRepository;
import com.bookmanager.bookmanager.repositories.BookRepository;
import com.bookmanager.bookmanager.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookFamilyRepository bookFamilyRepository;


    public BookResponseDto insert(BookRequestDto bookRequestDto) {
        try {
            byte[] fileContent = bookRequestDto.getFile().getBytes();
            Book book = new Book();
            book.setFileData(fileContent);
            book.setTitle(bookRequestDto.getTitle());
            book.setAuthor(bookRequestDto.getAuthor());
            book.setPublicationDate(bookRequestDto.getDate());
            var family = bookFamilyRepository.findById(bookRequestDto.getFamilyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));

            book.setFamily(family);
            book.setTitle(bookRequestDto.getTitle());
            book = bookRepository.save(book);
            return new BookResponseDto(book);
        } catch (IOException | EntityNotFoundException e) {
            throw new RuntimeException();
        }
    }


}