package org.arteco.sersoc.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("oneColumnCache", "oneRowCache", "manyRowsCache");
        cacheManager.setCaffeine(caffeineConfig());
        cacheManager.registerCustomCache("oneColumnCache", Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(200)
                .recordStats()
                .build());
        cacheManager.registerCustomCache("oneRowCache", Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(200)
                .recordStats()
                .build());
        cacheManager.registerCustomCache("manyRowsCache", Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(300)
                .recordStats()
                .build());
        return cacheManager;
    }

    private Caffeine<Object, Object> caffeineConfig() {
        // Default configuration for caches
        return Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.HOURS)
                .maximumSize(100);
    }

}