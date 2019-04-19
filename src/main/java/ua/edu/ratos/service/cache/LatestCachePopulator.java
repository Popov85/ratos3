package ua.edu.ratos.service.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.repository.ResultRepository;
import ua.edu.ratos.service.SchemeService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import static java.util.stream.IntStream.range;
import static ua.edu.ratos.service.cache.CachePopulator.NOTHING_TO_LOAD;

/**
 * Used to populate cache with latest taken schemes.
 */
@Slf4j
@Service
@SuppressWarnings("SpellCheckingInspection")
class LatestCachePopulator {

    private static final int LATEST_RESULT_SIZE = 1000;

    private final SchemeService schemeService;

    private final ResultRepository resultRepository;

    private final QuestionsLoader questionsLoader;

    @Autowired
    LatestCachePopulator(SchemeService schemeService, ResultRepository resultRepository, QuestionsLoader questionsLoader) {
        this.schemeService = schemeService;
        this.resultRepository = resultRepository;
        this.questionsLoader = questionsLoader;
    }

    void loadMany() {
        log.debug("Latest schemes will be computed based on {} latest results", LATEST_RESULT_SIZE);
        Sort sort = new Sort(Sort.Direction.DESC, "resultId");
        Pageable pageable = PageRequest.of(0, LATEST_RESULT_SIZE, sort);
        // Select latest N schemes Ids from Results
        Slice<Long> schemes = resultRepository.findLatestTakenSchemesIds(pageable);
        if (!schemes.hasContent()) {
            log.debug(NOTHING_TO_LOAD);
            return;
        }
        Set<Long> unique = schemes.stream().collect(Collectors.toSet());
        log.debug("Found {} unique schemes that will be loaded", unique.size());
        for (Long id : unique) {
            Scheme scheme = schemeService.findByIdForSession(id);
            questionsLoader.loadThemes(scheme);
            log.debug("Finished loading a scheme, schemeId = {}", id);
        }
        log.info("Latetst schemes' questions have been successfully loaded to cache, quantity = {}", unique.size());
    }

    void loadManyInParallel(int threads) {
        log.info("Latest schemes will be computed based on {} latest results", LATEST_RESULT_SIZE);
        Sort sort = new Sort(Sort.Direction.DESC, "resultId");
        Pageable pageable = PageRequest.of(0, LATEST_RESULT_SIZE, sort);
        // Select latest N schemes Ids from Results
        Slice<Long> schemes = resultRepository.findLatestTakenSchemesIds(pageable);
        if (!schemes.hasContent()) {
            log.debug(NOTHING_TO_LOAD);
            return;
        }
        Set<Long> unique = schemes.stream().collect(Collectors.toSet());
        log.info("Found {} unique schemes that will be loaded in parallel, threads = {}", unique.size(), threads);
        List<Callable<Boolean>> callables = getTasks(unique);
        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        try {
            executorService.invokeAll(callables);
            log.info("Latetst schemes' questions have been successfully loaded to cache, quantity = {}", unique.size());
        } catch (Exception e) {
            log.error("Failure executing parallel tasks, message = {}", e.getMessage());
        } finally {
            executorService.shutdown();
        }
    }

    private List<Callable<Boolean>> getTasks(Set<Long> schemes) {
        List<Callable<Boolean>> tasks = new ArrayList<>();
        List<Long> ids = new ArrayList<>(schemes);
        range(0, ids.size()).forEach(i -> {
            tasks.add(() -> {
                try {
                    Scheme scheme = schemeService.findByIdForSession(ids.get(i));
                    questionsLoader.loadThemes(scheme);
                    log.info("Finished loading a scheme, schemeId = {}, thread = {}", ids.get(i), Thread.currentThread().getName());
                } catch (Exception e) {
                    log.error("Error executing tasks in thread = {}", Thread.currentThread().getName());
                    return false;
                }
                return true;
            });
        });
        return tasks;
    }
}
