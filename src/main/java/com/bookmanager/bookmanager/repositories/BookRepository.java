package com.bookmanager.bookmanager.repositories;

import com.bookmanager.bookmanager.entities.Book;
import com.bookmanager.bookmanager.projections.BookProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT book.id AS id, book.title AS title, book.author AS author, book.publicationDate AS publicationDate, book.family AS family FROM Book book WHERE book.family IS NOT NULL")
    Page<BookProjection> findAllProjection(Pageable pageable);


}
