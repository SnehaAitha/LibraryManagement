package com.library.LibraryManagement.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.library.LibraryManagement.domain.Book;
import com.library.LibraryManagement.repository.BookManagementRepository;
import com.library.LibraryManagement.service.BookManagementService;

@Service("BookManagementService")
public class BookManagementServiceImpl implements BookManagementService {
	
	@Autowired
	BookManagementRepository bookManagementRepo;
	
	@Override
	@Transactional
	@Cacheable("books")
	public List<Book> fetchAllBooks() throws Exception {
		return bookManagementRepo.findAll();
	}	
	
	@Override
	@Transactional
	public Book addBook(Book book) throws Exception {
		return bookManagementRepo.save(book);
	}
	
	@Override
	@Transactional
	@Cacheable("bookById")
	public Optional<Book> fetchBookDetailsById(Long id) throws Exception {
		return bookManagementRepo.findById(id);
	}

	@Override
	@Transactional
	public Book updateBook(Book book,Book existingBook) throws Exception {
		if(book.getTitle() != null) {
			existingBook.setTitle(book.getTitle());
		}
		if(book.getAuthor() != null) {
			existingBook.setAuthor(book.getAuthor());
		}
		if(book.getPublicationYear() != 0) {
			existingBook.setPublicationYear(book.getPublicationYear());
		}
		if(book.getIsbn() != null) {
			existingBook.setIsbn(book.getIsbn());
		}
		return bookManagementRepo.save(existingBook);
	}

	@Override
	@Transactional
	public void deleteBookById(Long id) throws Exception {
		bookManagementRepo.deleteById(id);
	}
}