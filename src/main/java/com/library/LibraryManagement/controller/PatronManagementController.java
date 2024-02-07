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
import com.library.LibraryManagement.domain.Patron;
import com.library.LibraryManagement.response.Response;
import com.library.LibraryManagement.service.CachingService;
import com.library.LibraryManagement.service.PatronManagementService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/patrons")
public class PatronManagementController {
	
	@Autowired
	PatronManagementService patronService;
	
	@Autowired
	CachingService cacheService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping()
	public Response<List<Patron>> fetchAllPatrons() {
		List<Patron> patrons = new ArrayList<Patron>();
		try {
			logger.debug("In fetchAllPatrons.");
			patrons = patronService.fetchAllPatrons();
			if(patrons != null && patrons.size() == 0) {
				logger.info("Patrons retrived successfully with no rows."+patrons);
				return new Response<List<Patron>>(HttpStatus.NO_CONTENT, "Patrons retrived successfully with no rows." , patrons);
			}
			logger.info("Patrons retrived successfully."+patrons);
			return new Response<List<Patron>>(HttpStatus.OK, "Patrons retrived successfully.", patrons);
		} catch (Exception ex) {
			logger.error("Patrons retrival failed with exception.",ex);
			return new Response<List<Patron>>(HttpStatus.INTERNAL_SERVER_ERROR, "Patrons retrival failed.");
		}
	}
	
	@PostMapping()
	public Response<Patron> addPatron(@Valid @RequestBody Patron patron) 
	{
		try {
			logger.debug("In addPatron.");
			cacheService.evictAllCaches();
		    Patron newPatron = patronService.addPatron(patron);
			if(newPatron != null) {
				logger.info("Patron details saved successfully."+newPatron);
				return new Response<Patron>(HttpStatus.OK, "Patron details saved successfully.", newPatron);
			}
			logger.info("Patron details saving failed."+patron);
			return new Response<Patron>(HttpStatus.NOT_ACCEPTABLE, "Patron details saving failed.",patron);
		} catch (Exception ex) {
			logger.error("Patron details save failed with exception.", ex);
			return new Response<Patron>(HttpStatus.INTERNAL_SERVER_ERROR, "Patron details saving failed with exception.");
		}
	}
	
	@GetMapping(value="/{id}")
	public Response<Patron> fetchPatronDetailsById(@PathVariable Long id) 
	{
		try {
			logger.debug("In fetchPatronDetailsById.");
			Optional<Patron> patron = patronService.fetchPatronDetailsById(id);
			if(patron != null && !patron.isEmpty()) {
				logger.info("Patron details fetched successfully."+patron);
				return new Response<Patron>(HttpStatus.OK, "Patron details fetched successfully.", patron.get());
			}
			logger.info("Patron details fetching failed.");
			return new Response<Patron>(HttpStatus.NOT_FOUND, "Patron details not found.",null);
		} catch (Exception ex) {
			logger.error("Patron details fetch failed with exception.", ex);
			return new Response<Patron>(HttpStatus.INTERNAL_SERVER_ERROR, "Patron details fetching failed with exception.");
		}
	}
	
	@PutMapping("/{id}")
	public Response<Patron> updatePatron(@RequestBody Patron patron,@PathVariable Long id) 
	{
		try {
			logger.debug("In updatePatron.");
			cacheService.evictAllCaches();
			Optional<Patron> existingPatron = patronService.fetchPatronDetailsById(id);
			if(existingPatron == null || existingPatron.isEmpty()) {
				return new Response<Patron>(HttpStatus.NOT_FOUND, "Patron with id "+id+" doesnt exist.", null);
			}
			Patron updatedPatron = patronService.updatePatron(patron,existingPatron.get());
			if(updatedPatron != null) {
				logger.info("Patron details updated successfully."+updatedPatron);
				return new Response<Patron>(HttpStatus.OK, "Patron details updated successfully.", updatedPatron);
			}
			logger.info("Patron details updating failed."+updatedPatron);
			return new Response<Patron>(HttpStatus.NOT_ACCEPTABLE, "Patron details updating failed.",updatedPatron);
		} catch (Exception ex) {
			logger.error("Patron details updation failed with exception.", ex);
			return new Response<Patron>(HttpStatus.INTERNAL_SERVER_ERROR, "Patron details updating failed with exception.");
		}
	}
	
	@DeleteMapping("/{id}")
	public Response<String> deletePatron(@PathVariable Long id) 
	{
		try {
			logger.debug("In deletePatron.");
			cacheService.evictAllCaches();
		    Optional<Patron> patron = patronService.fetchPatronDetailsById(id);
			if(patron == null || patron.isEmpty()) {
				logger.info("Patron details of id "+id+" not found");
				return new Response<String>(HttpStatus.NOT_FOUND, "Patron details of id "+id+" not found");
			}
			else {
				patronService.deletePatronById(id);
				cacheService.evictAllCaches();
				Optional<Patron> deletedPatron = patronService.fetchPatronDetailsById(id);
				if(deletedPatron == null || deletedPatron.isEmpty()) {
					logger.info("Patron details of id "+id+" deleted successfully.");
					return new Response<String>(HttpStatus.OK, "Patron details of id "+id+" deleted successfully.");
				}
				else {
					logger.info("Patron details  of id "+id+" deletion failed.");
					return new Response<String>(HttpStatus.NOT_ACCEPTABLE, "Patron details  of id "+id+" deletion failed.");
				}
			}
		} catch (Exception ex) {
			logger.error("Patron details deletion failed with exception.", ex);
			return new Response<String>(HttpStatus.INTERNAL_SERVER_ERROR, "Patron details of id "+id+ " deletion failed with exception.");
		}
	}

}
