package ua.edu.ratos.service.session;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.session.domain.BatchEvaluated;
import ua.edu.ratos.service.session.domain.SessionData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Pyramid mode implies that in case when it is enabled,
 * we shift incorrect questions to the end of the questions
 * list, just like we did with skipped questions;
 */
@Service
public class PyramidProcessorService {

    /**
     * Selects only incorrectly answered questions (score <100)
     * Partly correct are counted as incorrect;
     * @param sessionData
     * @param batchEvaluated
     * @return list of incorrectly answered if pyramid mode is enabled
     */
    public List<Long> getIncorrect(@NonNull final SessionData sessionData, @NonNull final BatchEvaluated batchEvaluated) {
        final boolean pyramid = sessionData.getScheme().getMode().isPyramid();
        List<Long> incorrect = new ArrayList<>();
        if (pyramid) {
            incorrect = batchEvaluated.getResponsesEvaluated()
                    .values()
                    .stream()
                    .filter(q -> q.getScore() < 100)
                    .map(q -> q.getQuestion().getQuestionId())
                    .collect(Collectors.toList());
        }
        return incorrect;
    }
}
