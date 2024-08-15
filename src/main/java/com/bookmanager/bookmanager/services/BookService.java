package com.bookmanager.bookmanager.services;

import com.bookmanager.bookmanager.dto.book.BookFileResponseDto;
import com.bookmanager.bookmanager.dto.book.BookRequestDto;
import com.bookmanager.bookmanager.dto.book.BookResponseDto;
import com.bookmanager.bookmanager.entities.Book;
import com.bookmanager.bookmanager.repositories.BookFamilyRepository;
import com.bookmanager.bookmanager.repositories.BookRepository;
import com.bookmanager.bookmanager.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookFamilyRepository bookFamilyRepository;

    @Transactional
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

    @Transactional(readOnly = true)
    public BookFileResponseDto findById(Long id) {
        var fileBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        return new BookFileResponseDto(fileBook);
    }
}
