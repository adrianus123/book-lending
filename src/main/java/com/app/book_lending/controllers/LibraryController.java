package com.app.book_lending.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.book_lending.models.Book;
import com.app.book_lending.models.BorrowTransaction;
import com.app.book_lending.services.LibraryService;

@RestController
@RequestMapping("/api/library")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @GetMapping("/books")
    public List<Book> getAvailableBooks() {
        return libraryService.getAllAvailableBooks();
    }

    @PostMapping("/borrow/{bookId}")
    public ResponseEntity<BorrowTransaction> borrowBook(@PathVariable Long bookId, @RequestParam String borrowerName) {
        BorrowTransaction transaction = libraryService.borrowBook(bookId, borrowerName);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/return/{bookId}")
    public ResponseEntity<String> returnBook(@PathVariable Long bookId) {
        libraryService.returnBook(bookId);
        return ResponseEntity.ok("Book returned successfully");
    }
}