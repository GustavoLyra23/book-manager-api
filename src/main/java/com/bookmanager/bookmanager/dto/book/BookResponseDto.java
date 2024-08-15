package com.bookmanager.bookmanager.dto.book;

import com.bookmanager.bookmanager.entities.Book;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BookResponseDto {

    private Long id;
    private String family;
    private String title;
    private String author;
    private LocalDate publishDate;

    public BookResponseDto(Book book) {
        id = book.getId();
        family = book.getFamily().getName();
        title = book.getTitle();
        author = book.getAuthor();
        publishDate = book.getPublicationDate();
    }

}
