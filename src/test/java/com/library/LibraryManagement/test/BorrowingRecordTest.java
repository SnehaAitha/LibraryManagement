package com.library.LibraryManagement.test;

import static org.junit.Assert.assertNotNull;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.library.LibraryManagement.domain.Book;
import com.library.LibraryManagement.domain.BorrowingRecord;
import com.library.LibraryManagement.domain.Patron;
import com.library.LibraryManagement.repository.BookManagementRepository;
import com.library.LibraryManagement.repository.BorrowingRecordRepository;
import com.library.LibraryManagement.repository.PatronManagementRepository;
import com.library.LibraryManagement.service.BorrowingRecordService;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Order(3)
public class BorrowingRecordTest {

	@Autowired
	BorrowingRecordService borrowService;

	@Autowired
	BorrowingRecordRepository borrowRepo;
	
	@Autowired
	PatronManagementRepository patronRepo;
	
	@Autowired
	BookManagementRepository bookRepo;

	@Test
	@Order(1)
	@Transactional
	public void borrowBook() throws Exception {
		Patron patron = patronRepo.findById(1L).get();
		Book book = bookRepo.findById(1L).get();
		BorrowingRecord record = borrowService.borrowBook(book,patron);
		assertNotNull(record.getBorrowingId());
	}

	@Test
	@Order(2)
	@Transactional
	public void recordBookReturn() throws Exception {
		BorrowingRecord returnRecord = new BorrowingRecord();
		Patron patron = patronRepo.findById(1L).get();
		Book book = bookRepo.findById(1L).get();
		BorrowingRecord record = borrowService.borrowBook(book,patron);
		returnRecord = borrowService.recordBookReturn(record);
		assertNotNull(returnRecord.getReturnDate());
	}

}
