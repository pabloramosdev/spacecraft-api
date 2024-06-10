package com.movies.spacecraft.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfiguration {

    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("spacecrafts");
    }

}
