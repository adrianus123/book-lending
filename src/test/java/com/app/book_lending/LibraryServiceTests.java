package com.app.book_lending;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.app.book_lending.models.Book;
import com.app.book_lending.models.BorrowTransaction;
import com.app.book_lending.repositories.BookRepository;
import com.app.book_lending.repositories.BorrowTransactionRepository;
import com.app.book_lending.services.LibraryService;

@SpringBootTest
class LibraryServiceTests {

    @Autowired
    private LibraryService libraryService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private BorrowTransactionRepository borrowTransactionRepository;

    @Test
    void testBorrowBookSuccess() {
        Book book = new Book(1L, "Book Title", "Author", true);
        BorrowTransaction transaction = new BorrowTransaction(1L, book, "John Doe", LocalDate.now());

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Mockito.when(borrowTransactionRepository.save(Mockito.any(BorrowTransaction.class))).thenReturn(transaction);

        BorrowTransaction result = libraryService.borrowBook(1L, "John Doe");

        assertEquals("John Doe", result.getBorrowerName());
        assertFalse(book.isAvailable());
    }

    @Test
    void testBorrowBookFailure_BookNotAvailable() {
        Book book = new Book(1L, "Book Title", "Author", false);

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        assertThrows(RuntimeException.class, () -> libraryService.borrowBook(1L, "John Doe"));
    }

    @Test
    void testReturnBook() {
        Book book = new Book(1L, "Book Title", "Author", false);

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        libraryService.returnBook(1L);

        assertTrue(book.isAvailable());
    }
}
