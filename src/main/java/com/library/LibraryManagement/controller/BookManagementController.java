package com.library.LibraryManagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.library.LibraryManagement.domain.Book;
import com.library.LibraryManagement.response.Response;
import com.library.LibraryManagement.service.BookManagementService;
import com.library.LibraryManagement.service.CachingService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BookManagementController {
	
	@Autowired
	BookManagementService bookService;
	
	@Autowired
	CachingService cacheService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping()
	public Response<List<Book>> fetchAllBooks() {
		List<Book> books = new ArrayList<Book>();
		try {
			logger.debug("In fetchAllBooks.");
			books = bookService.fetchAllBooks();
			if(books != null && books.size() == 0) {
				logger.info("Books retrived successfully with no rows.");
				return new Response<List<Book>>(HttpStatus.NO_CONTENT, "Books retrived successfully with no rows." , books);
			}
			logger.info("Books retrived successfully. "+books);
			return new Response<List<Book>>(HttpStatus.OK, "Books retrived successfully.", books);
		} catch (Exception ex) {
			logger.error("Books retrival failed with exception.", ex);
			return new Response<List<Book>>(HttpStatus.INTERNAL_SERVER_ERROR, "Books retrival failed.");
		}
	}
	
	@PostMapping()
	public Response<Book> addBook(@Valid @RequestBody Book book) 
	{
		try {
			logger.debug("In addBook.");
			cacheService.evictAllCaches();
		    Book newBook = bookService.addBook(book);
			if(newBook != null) {
				logger.info("Book details saved successfully. "+newBook);
				return new Response<Book>(HttpStatus.OK, "Book details saved successfully.", newBook);
			}
			logger.info("Book details saving failed."+newBook);
			return new Response<Book>(HttpStatus.NOT_ACCEPTABLE, "Book details saving failed.",newBook);
		} catch (Exception ex) {
			logger.error("Book details save failed with exception.", ex);
			return new Response<Book>(HttpStatus.INTERNAL_SERVER_ERROR, "Book details saving failed with exception.");
		}
	}
	
	@GetMapping(value="/{id}")
	public Response<Book> fetchBookDetailsById(@PathVariable Long id) 
	{
		try {
			logger.debug("In fetchBookDetailsById.");
			Optional<Book> book = bookService.fetchBookDetailsById(id);
			if(book != null && !book.isEmpty()) {
				logger.info("Book details fetched successfully. "+book);
				return new Response<Book>(HttpStatus.OK, "Book details fetched successfully.", book.get());
			}
			logger.info("Book details fetching failed. ");
			return new Response<Book>(HttpStatus.NOT_FOUND, "Book details not found.",null);
		} catch (Exception ex) {
			logger.error("Book details fetch failed with exception.", ex);
			return new Response<Book>(HttpStatus.INTERNAL_SERVER_ERROR, "Book details fetching failed with exception.");
		}
	}
	
	@PutMapping("/{id}")
	public Response<Book> updateBook(@RequestBody Book book,@PathVariable Long id) 
	{
		try {
			logger.debug("In updateBook.");
			cacheService.evictAllCaches();
			Optional<Book> existingBook = bookService.fetchBookDetailsById(id);
			if(existingBook == null || existingBook.isEmpty()) {
				return new Response<Book>(HttpStatus.NOT_FOUND, "Book with id "+id+" doesnt exist.", null);
			}
		    Book updatedBook = bookService.updateBook(book,existingBook.get());
			if(updatedBook != null) {
				logger.info("Book details updated successfully."+updatedBook);
				return new Response<Book>(HttpStatus.OK, "Book details updated successfully.", updatedBook);
			}
			logger.info("Book details updating failed."+updatedBook);
			return new Response<Book>(HttpStatus.NOT_ACCEPTABLE, "Book details updating failed.",updatedBook);
		} catch (Exception ex) {
			logger.error("Book details updation failed with exception.", ex);
			return new Response<Book>(HttpStatus.INTERNAL_SERVER_ERROR, "Book details updating failed with exception.");
		}
	}
	
	@DeleteMapping("/{id}")
	public Response<String> deleteBook(@PathVariable Long id) 
	{
		try {
			logger.debug("In deleteBook.");
			cacheService.evictAllCaches();
		    Optional<Book> book = bookService.fetchBookDetailsById(id);
			if(book == null || book.isEmpty()) {
				logger.info("Book details of id "+id+" not found");
				return new Response<String>(HttpStatus.NOT_FOUND, "Book details of id "+id+" not found");
			}
			else {
				bookService.deleteBookById(id);	  
				cacheService.evictAllCaches();
				Optional<Book> deletedBook = bookService.fetchBookDetailsById(id);
				if(deletedBook == null || deletedBook.isEmpty()) {
					logger.info("Book details of id "+id+" deleted successfully.");
					return new Response<String>(HttpStatus.OK, "Book details of id "+id+" deleted successfully.");
				}
				else { 
					logger.info("Book details  of id "+id+" deletion failed.");
					return new Response<String>(HttpStatus.NOT_ACCEPTABLE, "Book details  of id "+id+" deletion failed.");
				}
			}
		} catch (Exception ex) {
			logger.error("Book details deletion failed with exception.", ex);
			return new Response<String>(HttpStatus.INTERNAL_SERVER_ERROR, "Book details of id "+id+" deletion failed with exception.");
		}
	}

}
