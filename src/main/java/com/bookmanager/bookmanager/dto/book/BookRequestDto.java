package com.bookmanager.bookmanager.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@ToString
public class BookRequestDto {

    @NotNull(message = "file can't be null")
    private MultipartFile file;
    @NotBlank(message = "book must have a title")
    private String title;
    @NotBlank(message = "Book must have an author")
    private String author;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @Positive
    @NotNull(message = "book must have a family")
    private Long familyId;

}
