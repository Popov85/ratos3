package ua.edu.ratos.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableCaching
@Profile({"prod", "dev", "demo"})
public class CacheConfig {}
