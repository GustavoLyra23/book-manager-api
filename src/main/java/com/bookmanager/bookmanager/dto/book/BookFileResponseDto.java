package com.bookmanager.bookmanager.dto.book;

import com.bookmanager.bookmanager.entities.Book;
import lombok.Getter;

@Getter
public class BookFileResponseDto extends BookResponseDto {

    private byte[] file;

    public BookFileResponseDto(Book book) {
        super(book);
        file = book.getFileData();
    }
}
