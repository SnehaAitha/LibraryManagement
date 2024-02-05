package com.library.LibraryManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.library.LibraryManagement.service.CachingService;

@RestController
public class CachingController {
	
    @Autowired
    CachingService cachingService;
	
    @GetMapping("/clearAllCaches")
    public void clearAllCaches() {
        cachingService.evictAllCaches();
    }
}
