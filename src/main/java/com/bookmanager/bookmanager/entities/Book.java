package com.bookmanager.bookmanager.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "tb_book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private LocalDate publicationDate;

    @ManyToOne
    @JoinColumn(name = "family_id")
    private BookFamily family;


    @Lob
    private byte[] fileData;


}
