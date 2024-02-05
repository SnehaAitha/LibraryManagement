package com.library.LibraryManagement.service;

import com.library.LibraryManagement.domain.Book;
import com.library.LibraryManagement.domain.BorrowingRecord;
import com.library.LibraryManagement.domain.Patron;

public interface BorrowingRecordService {

	public BorrowingRecord borrowBook(Book book, Patron patron) throws Exception;
	public BorrowingRecord findByBookAndPatron(Book book,Patron patron) throws Exception;
	public BorrowingRecord recordBookReturn(BorrowingRecord record) throws Exception;
	
}