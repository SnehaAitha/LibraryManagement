package com.library.LibraryManagement.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.library.LibraryManagement.domain.Patron;
import com.library.LibraryManagement.repository.PatronManagementRepository;
import com.library.LibraryManagement.service.PatronManagementService;

@Service("PatronManagementService")
public class PatronManagementServiceImpl implements PatronManagementService {
	
	@Autowired
	PatronManagementRepository patronManagementRepo;
	
	@Override
	@Transactional
	@Cacheable("patrons")
	public List<Patron> fetchAllPatrons() throws Exception {
		return patronManagementRepo.findAll();
	}	
	
	@Override
	@Transactional
	public Patron addPatron(Patron patron) throws Exception {
		return patronManagementRepo.save(patron);
	}
	
	@Override
	@Transactional
	@Cacheable("patronById")
	public Optional<Patron> fetchPatronDetailsById(Long id) throws Exception {
		return patronManagementRepo.findById(id);
	}

	@Override
	@Transactional
	public Patron updatePatron(Patron patron,Patron existingPatron) throws Exception {
		if(patron.getContactInfo() != null) {
			existingPatron.setContactInfo(patron.getContactInfo());
		}
		if(patron.getName() != null) {
			existingPatron.setName(patron.getName());
		}
		return patronManagementRepo.save(existingPatron);
	}
	
	@Override
	@Transactional
	public void deletePatronById(Long id) throws Exception {
		patronManagementRepo.deleteById(id);
	}
	
}