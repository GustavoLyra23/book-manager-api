package com.bookmanager.bookmanager.dto.bookfamiliy;

import com.bookmanager.bookmanager.entities.BookFamily;
import lombok.Getter;
import org.hibernate.validator.constraints.br.CPF;

@Getter
public class BookFamilyDto {


    private Long id;
    private String name;

    public BookFamilyDto(BookFamily bookFamily) {
        id = bookFamily.getId();
        name = bookFamily.getName();
    }


}
