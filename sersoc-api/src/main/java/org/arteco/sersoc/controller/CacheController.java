package org.arteco.sersoc.controller;

import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
public class CacheController {

    private final CacheManager cacheManager;

    public CacheController(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @GetMapping("/stats")
    public String getCacheStats() {
        StringBuilder statsBuilder = new StringBuilder();

        cacheManager.getCacheNames().forEach(cacheName -> {
            com.github.benmanes.caffeine.cache.Cache<?, ?> cache = (com.github.benmanes.caffeine.cache.Cache<?, ?>) cacheManager.getCache(cacheName).getNativeCache();
            statsBuilder.append("Cache Name: ").append(cacheName)
                    .append("\nStats: ").append(cache.stats().toString())
                    .append("\n\n");
        });

        return statsBuilder.toString();
    }
}