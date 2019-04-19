package ua.edu.ratos.service.cache;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.Scheme;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static ua.edu.ratos.service.cache.BatchProvider.BATCH_SIZE;

@Slf4j
@Service
@SuppressWarnings("SpellCheckingInspection")
class CachePopulator {

    static final String NOTHING_TO_LOAD = "There is nothing in database to load to cache";

    private final BatchLoader cachePopulatorHelper;

    @Autowired
    CachePopulator(BatchLoader cachePopulatorHelper) {
        this.cachePopulatorHelper = cachePopulatorHelper;
    }

    void loadMany(@NonNull final BatchProvider batchProvider, Object... params) {
        Pageable pageable = batchProvider.getPageable();
        Slice<Scheme> schemes = batchProvider.getBatch(pageable, params);
        if (!schemes.hasContent()) {
            log.debug(NOTHING_TO_LOAD);
            return;
        }
        cachePopulatorHelper.doLoad(schemes, pageable.getPageNumber());
        while (schemes.hasNext()) {
            pageable = schemes.nextPageable();
            schemes = batchProvider.getBatch(pageable, params);
            cachePopulatorHelper.doLoad(schemes, pageable.getPageNumber());
        }
        log.debug("Questions have been successfully loaded to cache");
    }

    void loadManyInParallel(@NonNull final BatchProvider batchProvider, int threads, Object... params) {
        Pageable pageable = batchProvider.getPageable();
        Slice<Scheme> schemes = batchProvider.getBatch(pageable, params);
        if (!schemes.hasContent()) {
            log.info(NOTHING_TO_LOAD);
            return;
        }
        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        try {
            log.info("Questions will be loaded in parallel, threads = {} in batches of size = {}", threads, BATCH_SIZE);
            cachePopulatorHelper.doLoad(executorService, schemes, pageable.getPageNumber());
            while (schemes.hasNext()) {
                pageable = schemes.nextPageable();
                schemes = batchProvider.getBatch(pageable, params);
                cachePopulatorHelper.doLoad(executorService, schemes, pageable.getPageNumber());
            }
            log.info("All requested schemes' questions have been successfully loaded to cache");
        } catch (Exception e) {
            log.error("Error executing parallel task of loading questions to cache, message = {}", e.getMessage());
        } finally {
            executorService.shutdown();
        }
    }
}
