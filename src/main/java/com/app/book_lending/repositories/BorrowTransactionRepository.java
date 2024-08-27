package com.app.book_lending.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.book_lending.models.BorrowTransaction;

@Repository
public interface BorrowTransactionRepository extends JpaRepository<BorrowTransaction, Long> {
}
