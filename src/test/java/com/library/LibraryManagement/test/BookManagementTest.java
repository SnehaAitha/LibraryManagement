package com.library.LibraryManagement.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.Optional;
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
import com.library.LibraryManagement.domain.Book;
import com.library.LibraryManagement.repository.BookManagementRepository;
import com.library.LibraryManagement.service.BookManagementService;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Order(1)
public class BookManagementTest {
	
	@Autowired
	BookManagementService bookService;
	
	@Autowired
	BookManagementRepository bookRepo;

	@Test
	@Order(1)
	public void addBook() throws Exception {
		Book book = new Book();
		Book saveBook = new Book();

		book.setId(1L);
		book.setTitle("Book 1");
		book.setAuthor("Author 1");
		book.setPublicationYear(1990);
		book.setIsbn("1239074289135");

		saveBook = bookService.addBook(book);
		assertNotNull(saveBook.getId());
	}
	
	@Test
	@Order(2)
	public void fetchAllBooks() throws Exception {
		List<Book> list = bookService.fetchAllBooks();
		System.out.print("id value " +list.size());
		assertTrue(list.size()>0);
	}

	@Test
	@Order(3)
	public void getBookDetailsById() throws Exception {
		Optional<Book> book = bookService.fetchBookDetailsById(1L);
		assertNotNull(book.get().getId());
	}

	@Test
	@Order(4)
	public void updateBook() throws Exception {
		Book book = bookService.fetchBookDetailsById(1L).get();
		Book updateBook = book;
		updateBook.setTitle("Book Title Updated");
		updateBook.setAuthor("Author Updated");
		updateBook.setPublicationYear(1992);
		updateBook.setIsbn("8239454209135");

		assertFalse(book.equals(bookRepo.save(updateBook)));
	}

}
