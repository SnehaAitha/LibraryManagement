package com.library.LibraryManagement.service;

import java.util.List;
import java.util.Optional;
import com.library.LibraryManagement.domain.Patron;

public interface PatronManagementService {
	
	public List<Patron> fetchAllPatrons() throws Exception;
	public Patron addPatron(Patron patron) throws Exception;
	public Optional<Patron> fetchPatronDetailsById(Long id) throws Exception;
	public Patron updatePatron(Patron patron, Patron existingPatron) throws Exception;
	public void deletePatronById(Long id) throws Exception;
		
}
