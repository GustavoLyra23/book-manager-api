package com.bookmanager.bookmanager.services;

import com.bookmanager.bookmanager.dto.bookfamiliy.BookFamilyDto;
import com.bookmanager.bookmanager.repositories.BookFamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookFamilyService {

    @Autowired
    private BookFamilyRepository bookFamilyRepository;


    public Page<BookFamilyDto> findAll(Pageable pageable) {
        return bookFamilyRepository.findAll(pageable).map(BookFamilyDto::new);
    }
}
