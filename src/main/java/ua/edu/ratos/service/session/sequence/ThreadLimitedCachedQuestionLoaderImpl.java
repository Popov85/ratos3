package ua.edu.ratos.service.session.sequence;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.service.QuestionService;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class ThreadLimitedCachedQuestionLoaderImpl extends CachedQuestionLoaderImpl {

    // Max threads doing job simultaneously
    private static final int PERMITS = 10;
    private static final long THREAD_AWAIT_TIMEOUT = 5;

    private Semaphore semaphore = new Semaphore(PERMITS, true);

    public ThreadLimitedCachedQuestionLoaderImpl(QuestionService questionService) {
        super(questionService);
    }

    @Override
    public Map<Affiliation, Set<Question>> loadAllQuestionsToMap(@NonNull final Scheme scheme) {
        try {
            if (semaphore.tryAcquire(THREAD_AWAIT_TIMEOUT, TimeUnit.SECONDS)){
                try {
                    Map<Affiliation, Set<Question>> result = super.loadAllQuestionsToMap(scheme);
                    return result;
                } finally {
                    semaphore.release();
                }
            } else {
                log.warn("Rejected request due to too many connections, schemeId = {}", scheme.getSchemeId());
                throw new RuntimeException("Try later, too many connections..., schemeId = "+scheme.getSchemeId());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String name() {
        return "thread-limited";
    }
}
