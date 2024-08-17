package com.bookmanager.bookmanager.factory;

import com.bookmanager.bookmanager.dto.book.BookRequestDto;
import com.bookmanager.bookmanager.entities.Book;
import com.bookmanager.bookmanager.entities.BookFamily;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

public class Factory {


    public static BookRequestDto createBookRequestDto() {
        MultipartFile multipartFile =
                new MockMultipartFile("file", "test.jpg", "text/plain", "hello".getBytes());
        return new BookRequestDto(multipartFile, "tituloTeste", "gustavo", LocalDate.now(), 1L);
    }

    public static BookFamily createBookFamily() {
        return new BookFamily(1L, "History");
    }

    public static Book createBook() throws IOException {
        var dto = createBookRequestDto();
        try {
            return new Book(null, dto.getTitle(), dto.getAuthor(), dto.getDate(),
                    new BookFamily(dto.getFamilyId(), "History"), dto.getFile().getBytes());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

}
