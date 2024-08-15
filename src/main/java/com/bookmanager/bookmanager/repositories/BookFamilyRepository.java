package com.bookmanager.bookmanager.repositories;

import com.bookmanager.bookmanager.entities.BookFamily;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookFamilyRepository extends JpaRepository<BookFamily, Long> {
}
