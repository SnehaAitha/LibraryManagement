package com.library.LibraryManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.library.LibraryManagement.domain.Book;
import com.library.LibraryManagement.domain.BorrowingRecord;
import com.library.LibraryManagement.domain.Patron;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord,Long> {

	BorrowingRecord findByBookAndPatron(Book book, Patron patron);
	
}