package com.library.LibraryManagement.service;

import java.util.List;
import java.util.Optional;
import com.library.LibraryManagement.domain.Book;

public interface BookManagementService {
	
	public List<Book> fetchAllBooks() throws Exception;
	public Book addBook(Book book) throws Exception;
	public Optional<Book>  fetchBookDetailsById(Long id) throws Exception;
	public void deleteBookById(Long id) throws Exception;
	public Book updateBook(Book book, Book existingBook) throws Exception;
		
}
