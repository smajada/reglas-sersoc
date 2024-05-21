package org.arteco.sersoc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.arteco.sersoc.config.SecurityConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
@SecurityRequirements({
        @SecurityRequirement(name = SecurityConfiguration.BEARER_AUTH)
})
@Tag(name = "CacheController", description = "RestController for cache stats")
public class CacheController {

    private final CacheManager cacheManager;

    public CacheController(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Operation(summary = "Caché endpoint" , description = "Caché endpoint to check if the API is working properly")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden - API key is missing or invalid")
    })
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