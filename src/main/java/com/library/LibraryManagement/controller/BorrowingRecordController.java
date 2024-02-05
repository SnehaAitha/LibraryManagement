package com.library.LibraryManagement.controller;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import com.library.LibraryManagement.domain.Book;
import com.library.LibraryManagement.domain.BorrowingRecord;
import com.library.LibraryManagement.domain.Patron;
import com.library.LibraryManagement.response.Response;
import com.library.LibraryManagement.service.BookManagementService;
import com.library.LibraryManagement.service.BorrowingRecordService;
import com.library.LibraryManagement.service.CachingService;
import com.library.LibraryManagement.service.PatronManagementService;

@RestController
public class BorrowingRecordController {
	
	@Autowired
	BorrowingRecordService borrowingService;
	
	@Autowired
	BookManagementService bookService;

	@Autowired
	PatronManagementService patronService;
	
	@Autowired
	CachingService cacheService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping("/borrow/{bookId}/patron/{patronId}")
	public Response<BorrowingRecord> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) 
	{
		BorrowingRecord borrowingRecord = null;
		try {
			logger.debug("In borrowBook.");
			cacheService.evictAllCaches();
			Optional<Book >book = bookService.fetchBookDetailsById(bookId);
			Optional<Patron> patron = patronService.fetchPatronDetailsById(patronId);
			if(book.isEmpty()) {
				return new Response<BorrowingRecord>(HttpStatus.NOT_FOUND, "Book doesnt exist.", null);
			}
			if(patron.isEmpty()) {
				return new Response<BorrowingRecord>(HttpStatus.NOT_FOUND, "Patron doesnt exist.", null);
			}
			BorrowingRecord record = borrowingService.findByBookAndPatron(book.get(), patron.get());		    
			if(record != null) {
				return new Response<BorrowingRecord>(HttpStatus.NOT_ACCEPTABLE, "Borrowing Record details already exists for this book and patron.", record);
			}
			borrowingRecord = borrowingService.borrowBook(book.get(),patron.get());
			if(borrowingRecord != null) {
				logger.info("Borrowing Record details saved successfully."+borrowingRecord);
				return new Response<BorrowingRecord>(HttpStatus.OK, "Borrowing Record details saved successfully.", borrowingRecord);
			}
			logger.info("Borrowing Record details saving failed."+borrowingRecord);
			return new Response<BorrowingRecord>(HttpStatus.NOT_ACCEPTABLE, "Borrowing Record details saving failed.",borrowingRecord);
		} catch (Exception ex) {
			logger.error("Borrowing Record details save failed with exception.", ex);
			return new Response<BorrowingRecord>(HttpStatus.INTERNAL_SERVER_ERROR, "Borrowing Record details saving failed with exception.");
		}
	}
	
	@PutMapping("/return/{bookId}/patron/{patronId}")
	public Response<BorrowingRecord> recordBookReturn(@PathVariable Long bookId, @PathVariable Long patronId) 
	{
		BorrowingRecord returnRecord = null;
		try {
			logger.debug("In recordBookReturnByPatron.");
			cacheService.evictAllCaches();
			Optional<Book >book = bookService.fetchBookDetailsById(bookId);
			Optional<Patron> patron = patronService.fetchPatronDetailsById(patronId);
			if(book.isEmpty()) {
				return new Response<BorrowingRecord>(HttpStatus.NOT_FOUND, "Book doesnt exist.", null);
			}
			if(patron.isEmpty()) {
				return new Response<BorrowingRecord>(HttpStatus.NOT_FOUND, "Patron doesnt exist.", null);
			}
			BorrowingRecord record = borrowingService.findByBookAndPatron(book.get(), patron.get());
			if(record == null) {
				return new Response<BorrowingRecord>(HttpStatus.NOT_ACCEPTABLE, "Borrowing Record details doesnt exist for this book and patron.", record);
			}
			returnRecord = borrowingService.recordBookReturn(record);
			if(returnRecord != null) {
				logger.info("Return Record details saved successfully."+returnRecord);
				return new Response<BorrowingRecord>(HttpStatus.OK, "Return Record details saved successfully.", returnRecord);
			}
			logger.info("Return Record details saving failed."+returnRecord);
			return new Response<BorrowingRecord>(HttpStatus.NOT_ACCEPTABLE, "Return Record details saving failed.",returnRecord);
		} catch (Exception ex) {
			logger.error("Return Record details save failed with exception.", ex);
			return new Response<BorrowingRecord>(HttpStatus.INTERNAL_SERVER_ERROR, "Return Record details saving failed with exception.");
		}
	}

}
