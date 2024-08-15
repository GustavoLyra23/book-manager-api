package com.bookmanager.bookmanager.repositories;

import com.bookmanager.bookmanager.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
