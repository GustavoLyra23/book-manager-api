package com.bookmanager.bookmanager.service;

import com.bookmanager.bookmanager.dto.book.BookRequestDto;
import com.bookmanager.bookmanager.entities.Book;
import com.bookmanager.bookmanager.entities.BookFamily;
import com.bookmanager.bookmanager.factory.Factory;
import com.bookmanager.bookmanager.repositories.BookFamilyRepository;
import com.bookmanager.bookmanager.repositories.BookRepository;
import com.bookmanager.bookmanager.services.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookServiceTest;

    @Mock
    private BookFamilyRepository bookFamilyRepository;

    @Captor
    ArgumentCaptor<Book> orderEntityCaptor;

    private long validFamilyId;
    private BookFamily validBookFamily;
    private Book validBook;
    private Book validBookWithId;
    private BookRequestDto bookRequestDto;

    @BeforeEach
    void setUp() throws IOException {
        //ARRANGE
        validFamilyId = 1L;
        validBookFamily = Factory.createBookFamily();
        validBook = Factory.createBook();
        validBookWithId = Factory.createBook();
        validBookWithId.setId(2L);
        bookRequestDto = Factory.createBookRequestDto();

        Mockito.when(bookFamilyRepository.findById(validFamilyId)).thenReturn(Optional.of(validBookFamily));
        Mockito.when(bookRepository.save(any())).thenReturn(validBookWithId);
    }

    @Test
    public void insertShouldReturnDtoWhenArgumentIsValid() throws IOException {
        var dtoResponse = bookServiceTest.insert(bookRequestDto);
        Mockito.verify(bookRepository, Mockito.times(1)).save(orderEntityCaptor.capture());
        var captor = orderEntityCaptor.getValue();

        Assertions.assertEquals(captor.getAuthor(), bookRequestDto.getAuthor());
        Assertions.assertNotNull(dtoResponse);
        Assertions.assertNotNull(dtoResponse.getId());
        Assertions.assertEquals(dtoResponse.getTitle(), bookRequestDto.getTitle());
        Assertions.assertEquals(dtoResponse.getAuthor(), bookRequestDto.getAuthor());
        Assertions.assertEquals(dtoResponse.getPublishDate(), bookRequestDto.getDate());
        Assertions.assertNotNull(dtoResponse.getFamily());


    }


}
