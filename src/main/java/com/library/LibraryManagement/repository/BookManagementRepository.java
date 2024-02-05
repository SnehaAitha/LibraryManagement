package com.library.LibraryManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.library.LibraryManagement.domain.Book;

public interface BookManagementRepository extends JpaRepository<Book,Long> {
	
}