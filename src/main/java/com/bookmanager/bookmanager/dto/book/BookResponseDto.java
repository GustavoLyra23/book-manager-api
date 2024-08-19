package com.bookmanager.bookmanager.dto.book;

import com.bookmanager.bookmanager.entities.Book;
import com.bookmanager.bookmanager.projections.BookProjection;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import org.hibernate.validator.constraints.br.CPF;

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

    public BookResponseDto(BookProjection projection) {
        id = projection.getId();
        family = projection.getFamily().getName();
        title = projection.getTitle();
        author = projection.getAuthor();
        publishDate = projection.getPublicationDate();
    }
}
