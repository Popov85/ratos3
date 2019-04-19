package ua.edu.ratos.service.cache;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.Scheme;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import static java.util.stream.IntStream.range;

/**
 * Helper method that is used to load a batch of schemes
 */
@Slf4j
@Service
class BatchLoader {

    private final QuestionsLoader questionsLoader;

    @Autowired
    BatchLoader(QuestionsLoader questionsLoader) {
        this.questionsLoader = questionsLoader;
    }

    void doLoad(Slice<Scheme> batch, int page) {
        List<Scheme> content = batch.getContent();
        for (Scheme scheme : content) {
            questionsLoader.loadThemes(scheme);
            log.debug("Loaded a scheme to cache, schemeId = {}", scheme.getSchemeId());
        }
        log.debug("Finished loading a batch of schemes to cache, batch = {}", page);
    }

    void doLoad(ExecutorService executorService, Slice<Scheme> batch, int page) throws InterruptedException {
        List<Callable<Boolean>> tasks = getTasks(batch.getContent());
        executorService.invokeAll(tasks);
        log.info("Finished loading a batch of schemes to cache, batch = {}", page);
    }

    private List<Callable<Boolean>> getTasks(@NonNull final List<Scheme> schemes) {
        List<Callable<Boolean>> tasks = new ArrayList<>();
        range(0, schemes.size()).forEach(i -> {
            tasks.add(() -> {
                try {
                    questionsLoader.loadThemes(schemes.get(i));
                    log.info("Finished loading themes of schemeId = {}, thread = {}", schemes.get(i).getSchemeId(), Thread.currentThread().getName());
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
