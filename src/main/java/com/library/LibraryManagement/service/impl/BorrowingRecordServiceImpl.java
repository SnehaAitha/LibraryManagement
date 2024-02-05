package com.library.LibraryManagement.service.impl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.library.LibraryManagement.domain.Book;
import com.library.LibraryManagement.domain.BorrowingRecord;
import com.library.LibraryManagement.domain.Patron;
import com.library.LibraryManagement.repository.BorrowingRecordRepository;
import com.library.LibraryManagement.service.BorrowingRecordService;

@Service("BorrowingRecordService")
public class BorrowingRecordServiceImpl implements BorrowingRecordService {

	@Autowired
	BorrowingRecordRepository borrowingRecordRepo;

	@Override
	@Transactional
	public BorrowingRecord borrowBook(Book book, Patron patron) throws Exception {
		BorrowingRecord borrowingRecord = new BorrowingRecord();
		borrowingRecord.setPatron(patron);
		borrowingRecord.setBook(book);
		borrowingRecord.setBorrowingDate(new Date());
		patron.addBorrowingRecord(borrowingRecord);
		book.addBorrowingRecord(borrowingRecord);
		return borrowingRecordRepo.save(borrowingRecord);
	}

	@Override
	@Transactional
	public BorrowingRecord recordBookReturn(BorrowingRecord record) throws Exception {
		record.setReturnDate(new Date());
		return borrowingRecordRepo.save(record);
	}

	@Override
	public BorrowingRecord findByBookAndPatron(Book book, Patron patron) throws Exception {
		return borrowingRecordRepo.findByBookAndPatron(book, patron);
	}


}