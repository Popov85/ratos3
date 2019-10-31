package ua.edu.ratos.service.session;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.dto.session.question.QuestionSessionOutDto;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class NextBatchQuestionsProducer {

    private final BatchQuestionsTransformAndShuffleService batchQuestionsTransformAndShuffleService;

    protected List<QuestionSessionOutDto> getBatchQuestions(@NonNull final SessionData sessionData) {
        if (!sessionData.hasMoreQuestions()) throw new IllegalStateException("Wrong API usage: no more questions");

        final List<QuestionDomain> questionDomains = sessionData.getSequence();
        final int size = questionDomains.size();
        final int currentIndex = sessionData.getCurrentIndex();
        if (currentIndex >= size) throw new RuntimeException("Ops, no more questions!");
        final int questionsPerBatch = sessionData.getSchemeDomain().getSettingsDomain().getQuestionsPerSheet();
        List<QuestionDomain> result;
        if (questionsPerBatch<=0 || currentIndex + questionsPerBatch > size) {
            result = new ArrayList<>(questionDomains.subList(currentIndex, size));
        } else {
            result = new ArrayList<>(questionDomains.subList(currentIndex, currentIndex+questionsPerBatch));
        }
        // Transform to session DTO form
        List<QuestionSessionOutDto> collect = batchQuestionsTransformAndShuffleService.transformAndShuffle(result);
        return collect;
    }
}
