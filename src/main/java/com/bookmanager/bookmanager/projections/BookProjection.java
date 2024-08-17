package com.bookmanager.bookmanager.projections;

import com.bookmanager.bookmanager.entities.BookFamily;

import java.time.LocalDate;

public interface BookProjection {

    Long getId();

    String getTitle();

    String getAuthor();

    LocalDate getPublicationDate();

    BookFamily getFamily();


}
