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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BookControllerIT {


    @Autowired
    private MockMvc mockMvc;

    private BookRequestDto bookRequestDto;
    private String tokenJwt;
    private String expiredTokenJwt;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private BookFamilyRepository bookFamilyRepository;

    @Autowired
    private JwtDecoder jwtDecoder;

    @BeforeEach
    void setUp() throws Exception {
        bookRequestDto = Factory.createBookRequestDto();
        tokenJwt = tokenUtil.obtainAccessToken(mockMvc);
        expiredTokenJwt = tokenUtil.generateExpiredToken(mockMvc);
    }


    @Test
    public void addBookShouldReturn200AndJsonWhenValidArgumentsAndAuthentication() throws Exception {

        ResultActions resultActions = mockMvc.perform(multipart("/books")
                .file((MockMultipartFile) bookRequestDto.getFile())
                .param("title", bookRequestDto.getTitle())
                .param("author", bookRequestDto.getAuthor())
                .param("date", bookRequestDto.getDate().toString())
                .param("familyId", bookRequestDto.getFamilyId().toString())
                .header("Authorization", "Bearer " + tokenJwt)
                .contentType(MediaType.MULTIPART_FORM_DATA));
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.id").isNotEmpty());
        resultActions.andExpect(jsonPath("$.family")
                .value(bookFamilyRepository.findById(bookRequestDto.getFamilyId()).get().getName()));
        resultActions.andExpect(jsonPath("$.title").value(bookRequestDto.getTitle()));
        resultActions.andExpect(jsonPath("$.author").value(bookRequestDto.getAuthor()));
        resultActions.andExpect(jsonPath("$.publishDate").value(bookRequestDto.getDate().toString()));
    }

    @Test
    public void addBookShouldReturn400whenInvalidAuthentication() throws Exception {
        ResultActions resultActions = mockMvc.perform(multipart("/books")
                .file((MockMultipartFile) bookRequestDto.getFile())
                .param("title", bookRequestDto.getTitle())
                .param("author", bookRequestDto.getAuthor())
                .param("date", bookRequestDto.getDate().toString())
                .param("familyId", bookRequestDto.getFamilyId().toString())
                .header("Authorization", "Bearer " + expiredTokenJwt)
                .contentType(MediaType.MULTIPART_FORM_DATA));
        resultActions.andExpect(status().is4xxClientError());

    }
}
