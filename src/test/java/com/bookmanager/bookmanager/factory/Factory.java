package com.bookmanager.bookmanager.factory;

import com.bookmanager.bookmanager.dto.book.BookRequestDto;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class Factory {


    public static BookRequestDto createBookRequestDto() {
        MultipartFile multipartFile =
                new MockMultipartFile("file", "test.jpg", "text/plain", "hello".getBytes());
        return new BookRequestDto(multipartFile, "tituloTeste", "gustavo", LocalDate.now(), 1L);
    }


}
