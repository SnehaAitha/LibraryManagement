package com.library.LibraryManagement.test;

import java.util.Optional;
import org.junit.Assert;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.library.LibraryManagement.domain.Book;
import com.library.LibraryManagement.domain.Patron;
import com.library.LibraryManagement.repository.BookManagementRepository;
import com.library.LibraryManagement.repository.PatronManagementRepository;
import com.library.LibraryManagement.service.BookManagementService;
import com.library.LibraryManagement.service.PatronManagementService;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Order(4)
public class DeletionTest {
	
	@Autowired
	PatronManagementService patronService;
	
	@Autowired
	PatronManagementRepository patronRepo;
	
	@Autowired
	BookManagementService bookService;
	
	@Autowired
	BookManagementRepository bookRepo;
	
	@Test
	public void deletePatron() throws Exception {
		Patron patron = patronRepo.findById(1L).get();
		patronService.deletePatron(patron);
		Assert.assertEquals(Optional.empty(),patronRepo.findById(1L));
	}
	
	@Test
	public void deleteBook() throws Exception {
		Book book = bookRepo.findById(1L).get();
		bookService.deleteBook(book);
		Assert.assertEquals(Optional.empty(),bookRepo.findById(1L));
	}


}
