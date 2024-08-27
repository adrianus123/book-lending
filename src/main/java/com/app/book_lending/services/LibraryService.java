package com.app.book_lending.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.book_lending.models.Book;
import com.app.book_lending.models.BorrowTransaction;
import com.app.book_lending.repositories.BookRepository;
import com.app.book_lending.repositories.BorrowTransactionRepository;

@Service
public class LibraryService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowTransactionRepository borrowTransactionRepository;

    public List<Book> getAllAvailableBooks() {
        return bookRepository.findByAvailable(true);
    }

    public BorrowTransaction borrowBook(Long bookId, String borrowerName) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        if (!book.isAvailable()) {
            throw new RuntimeException("Book is not available for borrowing");
        }
        book.setAvailable(false);
        bookRepository.save(book);

        BorrowTransaction transaction = new BorrowTransaction();
        transaction.setBook(book);
        transaction.setBorrowerName(borrowerName);
        transaction.setBorrowDate(LocalDate.now());
        return borrowTransactionRepository.save(transaction);
    }

    public void returnBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        book.setAvailable(true);
        bookRepository.save(book);
    }
}
