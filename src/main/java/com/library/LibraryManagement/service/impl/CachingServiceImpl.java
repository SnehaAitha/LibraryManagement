package com.library.LibraryManagement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import com.library.LibraryManagement.service.CachingService;

@Service("CachingService")
public class CachingServiceImpl implements CachingService {
	
	@Autowired
	CacheManager cacheManager;
	
	public void evictAllCaches() {
	    cacheManager.getCacheNames().stream()
	      .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
	}

}
