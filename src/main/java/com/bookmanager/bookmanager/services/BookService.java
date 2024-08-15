package com.bookmanager.bookmanager.services;

import com.bookmanager.bookmanager.dto.book.BookFileResponseDto;
import com.bookmanager.bookmanager.dto.book.BookRequestDto;
import com.bookmanager.bookmanager.dto.book.BookResponseDto;
import com.bookmanager.bookmanager.entities.Book;
import com.bookmanager.bookmanager.repositories.BookFamilyRepository;
import com.bookmanager.bookmanager.repositories.BookRepository;
import com.bookmanager.bookmanager.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public BookResponseDto insert(BookRequestDto bookRequestDto) throws IOException {

        byte[] fileContent = bookRequestDto.getFile().getBytes();
        Book book = new Book();
        book.setFileData(fileContent);
        book.setTitle(bookRequestDto.getTitle());
        book.setAuthor(bookRequestDto.getAuthor());
        book.setPublicationDate(bookRequestDto.getDate());
        var family = bookFamilyRepository.findById(bookRequestDto.getFamilyId())
                .orElseThrow(() -> new ResourceNotFoundException("book family not found"));

        book.setFamily(family);
        book.setTitle(bookRequestDto.getTitle());
        book = bookRepository.save(book);
        return new BookResponseDto(book);

    }

    @Transactional(readOnly = true)
    public BookFileResponseDto findById(Long id) {
        var fileBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("book not found"));
        return new BookFileResponseDto(fileBook);
    }

    @Transactional(readOnly = true)
    public Page<BookResponseDto> findAllPaged(Pageable pageable) {
        return bookRepository.findAll(pageable).map(BookResponseDto::new);
    }

    @Transactional
    public BookResponseDto update(BookRequestDto bookRequestDto, Long id) throws IOException {
        var book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book not found"));
        book.setTitle(bookRequestDto.getTitle());
        book.setAuthor(bookRequestDto.getAuthor());
        book.setPublicationDate(bookRequestDto.getDate());
        var family = bookFamilyRepository.findById(bookRequestDto.getFamilyId())
                .orElseThrow(() -> new ResourceNotFoundException("book family not found"));
        book.setFamily(family);
        book.setFileData(bookRequestDto.getFile().getBytes());
        book = bookRepository.save(book);
        return new BookResponseDto(book);

    }
}
