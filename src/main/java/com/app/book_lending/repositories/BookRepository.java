package com.app.book_lending.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.book_lending.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAvailable(boolean available);
}
