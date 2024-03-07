package ua.edu.ratos.service.bootstrap;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import ua.edu.ratos.config.properties.AppProperties;
import ua.edu.ratos.service.cache.CacheSupport;

@Slf4j
@Service
@SuppressWarnings("SpellCheckingInspection")
@AllArgsConstructor
public class CacheInitListener {

    private final AppProperties prop;

    private final CacheSupport cacheSupport;

    @Order(2)
    @EventListener(ContextRefreshedEvent.class)
    public void cache() {
        AppProperties.Init.Caching strategy = prop.getInit().getCache();
        int threads = prop.getInit().getCacheThreads();
        if (strategy.equals(AppProperties.Init.Caching.NONE)) {
            log.info("Cache will not be loaded at start-up");
        } else {
            cacheSupport.loadMany(strategy, threads);
        }
    }
}
