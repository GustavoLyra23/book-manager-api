package com.bookmanager.bookmanager.dto.bookfamiliy;

import com.bookmanager.bookmanager.entities.BookFamily;
import lombok.Getter;

@Getter
public class BookFamilyDto {


    private Long id;
    private String name;

    public BookFamilyDto(BookFamily bookFamily) {
        id = bookFamily.getId();
        name = bookFamily.getName();
    }


}
