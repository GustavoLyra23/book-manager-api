package com.bookmanager.bookmanager.dto.bookfamiliy;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookFamilyRequestDto {

    @NotBlank(message = "name cant be null")
    private String name;


}
