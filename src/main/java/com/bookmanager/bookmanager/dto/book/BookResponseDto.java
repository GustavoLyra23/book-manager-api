package com.bookmanager.bookmanager.dto.book;

import com.bookmanager.bookmanager.entities.Book;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BookResponseDto {

    private final Long id;
    private final String family;
    private final String title;
    private final String author;
    private final LocalDate publishDate;

    public BookResponseDto(Book book) {
        id = book.getId();
        family = book.getFamily().getName();
        title = book.getTitle();
        author = book.getAuthor();
        publishDate = book.getPublicationDate();
    }
}
