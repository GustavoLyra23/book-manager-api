package com.bookmanager.bookmanager.services;

import com.bookmanager.bookmanager.dto.bookfamiliy.BookFamilyDto;
import com.bookmanager.bookmanager.dto.bookfamiliy.BookFamilyRequestDto;
import com.bookmanager.bookmanager.entities.BookFamily;
import com.bookmanager.bookmanager.repositories.BookFamilyRepository;
import com.bookmanager.bookmanager.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookFamilyService {

    @Autowired
    private BookFamilyRepository bookFamilyRepository;

    @Transactional(readOnly = true)
    public Page<BookFamilyDto> findAll(Pageable pageable) {
        return bookFamilyRepository.findAll(pageable).map(BookFamilyDto::new);
    }

    @Transactional
    public BookFamilyDto insert(BookFamilyRequestDto bookFamilyRequestDto) {
        BookFamily bookFamily = new BookFamily();
        bookFamily.setName(bookFamilyRequestDto.getName());
        bookFamily = bookFamilyRepository.save(bookFamily);
        return new BookFamilyDto(bookFamily);
    }

    @Transactional
    public BookFamilyDto update(Long id, BookFamilyRequestDto bookFamilyRequestDto) {
        var book = bookFamilyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Family not found"));
        book.setName(bookFamilyRequestDto.getName());
        book = bookFamilyRepository.save(book);
        return new BookFamilyDto(book);
    }
}
