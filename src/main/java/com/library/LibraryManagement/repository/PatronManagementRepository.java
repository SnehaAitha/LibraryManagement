package com.library.LibraryManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.library.LibraryManagement.domain.Patron;

public interface PatronManagementRepository extends JpaRepository<Patron,Long> {
	
}