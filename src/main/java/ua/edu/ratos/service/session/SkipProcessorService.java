package ua.edu.ratos.service.session;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.dto.session.BatchIn;
import ua.edu.ratos.service.dto.session.OptionsDto;
import ua.edu.ratos.service.session.domain.SessionData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class SkipProcessorService {
    /**
     * Select only skipped question IDs, for shifting
     * @param batchIn
     * @param sessionData
     * @return list of ID of skipped questions, provided allowed by settings, empty list if not allowed
     */
    public List<Long> getSkipped(@NonNull final BatchIn batchIn, @NonNull final SessionData sessionData) {
        List<Long> skipped = new ArrayList<>();
        // Check if skip is allowed by settings
        final boolean skipable = sessionData.getScheme().getMode().isSkipable();
        if (skipable) {
            sessionData.getCurrentBatch().getBatch().forEach(b -> {
                final Long questionId = b.getQuestionId();
                final OptionsDto options = batchIn.getOptions().get(questionId);
                if (options != null && options.isSkipRequested()) skipped.add(questionId);
            });
        }
        return skipped;
    }

    /**
     * Select only non-skipped question IDs, for evaluating
     * @param skipped
     * @param sessionData
     * @return list of ID of not skipped questions
     */
    public List<Long> getNotSkipped(@NonNull final List<Long> skipped, @NonNull final SessionData sessionData) {
        final List<Long> all = sessionData.getCurrentBatch().getBatch()
                .stream()
                .map(q->q.getQuestionId())
                .collect(Collectors.toList());
        // all minus skipped
        all.removeAll(skipped);
        return all;
    }

}
