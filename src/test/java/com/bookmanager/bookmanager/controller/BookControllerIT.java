package com.bookmanager.bookmanager.controller;

import com.bookmanager.bookmanager.dto.book.BookRequestDto;
import com.bookmanager.bookmanager.factory.Factory;
import com.bookmanager.bookmanager.repositories.BookFamilyRepository;
import com.bookmanager.bookmanager.util.TokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BookControllerIT {


    @Autowired
    private MockMvc mockMvc;

    private BookRequestDto bookRequestDto;
    private String tokenJwt;
    private String expiredTokenJwt;
    private String nonAdminToken;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private BookFamilyRepository bookFamilyRepository;

    @Autowired
    private JwtDecoder jwtDecoder;

    private long nonExistingId;

    @BeforeEach
    void setUp() throws Exception {
        //ARRANGE
        bookRequestDto = Factory.createBookRequestDto();
        tokenJwt = tokenUtil.obtainAccessToken(mockMvc);
        expiredTokenJwt = tokenUtil.generateExpiredToken(mockMvc);
        nonExistingId = 100000000000000000L;
        nonAdminToken = tokenUtil.obtaiNonAdminAccessToken(mockMvc);
    }


    @Test
    public void addBookShouldReturn200AndJsonWhenValidArgumentsAndAuthentication() throws Exception {

        ResultActions resultActions = mockMvc.perform(multipart("/books")
                .file((MockMultipartFile) bookRequestDto.getFile()).param("title", bookRequestDto.getTitle())
                .param("author", bookRequestDto.getAuthor())
                .param("date", bookRequestDto.getDate().toString())
                .param("familyId", bookRequestDto.getFamilyId().toString())
                .header("Authorization", "Bearer " + tokenJwt)
                .contentType(MediaType.MULTIPART_FORM_DATA));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.id").isNotEmpty());
        resultActions.andExpect(jsonPath("$.family").value(bookFamilyRepository.findById(bookRequestDto.getFamilyId()).get().getName()));
        resultActions.andExpect(jsonPath("$.title").value(bookRequestDto.getTitle()));
        resultActions.andExpect(jsonPath("$.author").value(bookRequestDto.getAuthor()));
        resultActions.andExpect(jsonPath("$.publishDate").value(bookRequestDto.getDate().toString()));
    }

    @Test
    public void addBookShouldReturn400whenInvalidAuthentication() throws Exception {
        ResultActions resultActions = mockMvc.perform(multipart("/books").file((MockMultipartFile) bookRequestDto.getFile()).param("title", bookRequestDto.getTitle()).param("author", bookRequestDto.getAuthor()).param("date", bookRequestDto.getDate().toString()).param("familyId", bookRequestDto.getFamilyId().toString()).header("Authorization", "Bearer " + expiredTokenJwt).contentType(MediaType.MULTIPART_FORM_DATA));
        resultActions.andExpect(status().is4xxClientError());

    }

    @Test
    public void retrieveBookShouldReturn200AndByteFileWhenValidArgumentsAndAuthentication() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/books/v1/download/{id}", 1L));
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM));
        resultActions.andExpect(result -> {
            byte[] byteFile = result.getResponse().getContentAsByteArray();
            assertNotNull(byteFile);
            assertTrue(byteFile.length > 0);
        });
    }

    @Test
    public void retrieveBookShouldReturn4040WhenInvalidId() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/books/v1/download/{id}", nonExistingId)
                .contentType(MediaType.APPLICATION_JSON));


        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(jsonPath("$.timestamp").isNotEmpty());
        resultActions.andExpect(jsonPath("$.status").value(404));
        resultActions.andExpect(jsonPath("$.error").isNotEmpty());
        resultActions.andExpect(jsonPath("$.message").value("Resource not found"));
        resultActions.andExpect(jsonPath("$.path").value("/books/v1/download/" + nonExistingId));
    }

    @Test
    public void retrieveAllBooksShouldReturn200WhenValidParams() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        ResultActions resultActions = mockMvc.perform(get("/books")
                .param("size", String.valueOf(pageable.getPageSize()))
                .param("page", String.valueOf(pageable.getPageNumber()))
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.content").isArray());
        resultActions.andExpect(jsonPath("$.pageable.pageNumber").value(pageable.getPageNumber()));
        resultActions.andExpect(jsonPath("$.pageable.pageSize").value(pageable.getPageSize()));
    }

    @Test
    public void updateBookShouldReturn200andJsonWhenValidArgumentsAndAuthentication() throws Exception {
        ResultActions resultActions = mockMvc.perform(multipart("/books/v1/{id}", 1L)
                .file((MockMultipartFile) bookRequestDto.getFile())
                .with(request -> {
                    request.setMethod("PUT");
                    return request;
                })
                .param("title", "Harry Potter")
                .param("author", "Gustavo")
                .param("date", "2024-10-07")
                .param("familyId", "1")
                .header("Authorization", "Bearer " + tokenJwt)
                .contentType(MediaType.MULTIPART_FORM_DATA));
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.id").isNotEmpty());
        resultActions.andExpect(jsonPath("$.family").isNotEmpty());
        resultActions.andExpect(jsonPath("$.title").value("Harry Potter"));
        resultActions.andExpect(jsonPath("$.author").value("Gustavo"));
        resultActions.andExpect(jsonPath("$.publishDate").value("2024-10-07"));
        resultActions.andExpect(jsonPath("$.family").isNotEmpty());
    }

    @Test
    public void updateShoudlReturn403WhenInvalidAuthentication() throws Exception {
        ResultActions resultActions = mockMvc.perform(multipart("/books/v1/{id}", 1L)
                .file((MockMultipartFile) bookRequestDto.getFile())
                .with(request -> {
                    request.setMethod("PUT");
                    return request;
                })
                .param("title", "Harry Potter")
                .param("author", "Gustavo")
                .param("date", "2024-10-07")
                .param("familyId", "1")
                .header("Authorization", "Bearer " + nonAdminToken)
                .contentType(MediaType.MULTIPART_FORM_DATA));
        resultActions.andExpect(status().isForbidden());
    }


}
