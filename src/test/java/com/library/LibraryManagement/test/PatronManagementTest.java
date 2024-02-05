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
import com.library.LibraryManagement.domain.Patron;
import com.library.LibraryManagement.repository.PatronManagementRepository;
import com.library.LibraryManagement.service.PatronManagementService;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Order(2)
public class PatronManagementTest {
	
	@Autowired
	PatronManagementService patronService;
	
	@Autowired
	PatronManagementRepository patronRepo;
	
	@Test
	@Order(1)
	public void addPatron() throws Exception {
		Patron patron = new Patron();
		Patron savePatron = new Patron();

		patron.setID(1L);
		patron.setName("Patron 1");
		patron.setContactInfo("Contact 1");

		savePatron = patronService.addPatron(patron);
		assertNotNull(savePatron.getId());
	}
	
	@Test
	@Order(2)
	public void fetchAllPatrons() throws Exception {
		List<Patron> list = patronService.fetchAllPatrons();
		assertTrue(list.size() > 0);
	}
	
	@Test
	@Order(3)
	public void getPatronDetailsById() throws Exception {
		Optional<Patron> patron = patronService.fetchPatronDetailsById(1L);
		assertNotNull(patron.get().getId());
	}
	
	@Test
	@Order(4)
	public void updatePatron() throws Exception {
		Patron patron = patronService.fetchPatronDetailsById(1L).get();
		Patron updatePatron =patron;
		updatePatron.setName("Patron Updated");
		updatePatron.setContactInfo("Contact Updated");

		assertFalse(patron.equals(patronRepo.save(updatePatron)));
	}

}
