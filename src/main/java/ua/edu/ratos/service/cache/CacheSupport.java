package ua.edu.ratos.service.cache;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ua.edu.ratos.config.TrackTime;
import ua.edu.ratos.config.properties.AppProperties;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.service.SchemeService;

import static ua.edu.ratos.config.properties.AppProperties.Init.Caching.*;

@Slf4j
@Service
@SuppressWarnings("SpellCheckingInspection")
public class CacheSupport {

    private final SchemeService schemeService;

    private final QuestionsLoader questionsLoader;

    private final BatchProviderFactory batchProviderFactory;

    private final CachePopulator cachePopulator;

    private final LatestCachePopulator latestCachePopulator;

    @Autowired
    public CacheSupport(SchemeService schemeService, QuestionsLoader questionsLoader,
                        BatchProviderFactory batchProviderFactory, CachePopulator cachePopulator,
                        LatestCachePopulator latestCachePopulator) {
        this.schemeService = schemeService;
        this.questionsLoader = questionsLoader;
        this.batchProviderFactory = batchProviderFactory;
        this.cachePopulator = cachePopulator;
        this.latestCachePopulator = latestCachePopulator;
    }

    /**
     * Populates/re-populates questions cache for a certain scheme
     * @param schemeId
     */
    @Async
    @TrackTime
    public void loadScheme(@NonNull final Long schemeId) {
        Scheme scheme = schemeService.findByIdForSession(schemeId);
        if (scheme==null) throw new RuntimeException("Scheme not found, schemeId = "+schemeId);
        questionsLoader.loadThemes(scheme);
    }

    /**
     * Populates/re-populates questions cache for the entire course
     * @param courseId
     */
    @Async
    @TrackTime
    public void loadCourse(@NonNull final Long courseId) {
        cachePopulator.loadMany(batchProviderFactory.getInstance("course"), courseId);
    }

    /**
     * Populates/re-populates questions cache for the entire department
     */
    @Async
    @TrackTime
    public void loadDepartment(@NonNull final Long depId) {
        // depId we get from current user's authentication params
        cachePopulator.loadMany(batchProviderFactory.getInstance("department"), depId);
    }

    /**
     * Populates questions cache based on selected strategy at run-time
     * in a single thread so that not to degrade performance of the running app too much!
     * @param strategy the selected strategy
     */
    @Async
    @TrackTime
    public void loadMany(@NonNull final AppProperties.Init.Caching strategy) {
        if (strategy.equals(ALL)) {
            cachePopulator.loadMany(batchProviderFactory.getInstance("all"));
        } else if (strategy.equals(LARGE)) {
            cachePopulator.loadMany(batchProviderFactory.getInstance("large"));
        } else if (strategy.equals(LATEST)) {
            latestCachePopulator.loadMany();
        } else throw new UnsupportedOperationException("Unsupported cache loading option = "+strategy);
    }


    /**
     * Populates questions cache based on selected strategy at start-up.
     * It uses multile threads to speed up the process.
     * The app will not start accepting queries before the loading completes.
     */
    @TrackTime
    public void loadMany(@NonNull final AppProperties.Init.Caching strategy, int threads) {
        // This strategy takes the most time, but speeds up the runtime significantly
        if (strategy.equals(ALL)) {
            cachePopulator.loadManyInParallel(batchProviderFactory.getInstance("all"), threads);
        } else if (strategy.equals(LARGE)) { // Use this strategy is you have mainly heavy composite schemes in usage
            cachePopulator.loadManyInParallel(batchProviderFactory.getInstance("large"), threads);
        } else if (strategy.equals(LATEST)) { // Use this strategy if you want to load at start-up only the scheme's questions that were used recently based on results
            latestCachePopulator.loadManyInParallel(threads);
        } else throw new UnsupportedOperationException("Unsupported cache loading option = "+strategy);
    }
}
