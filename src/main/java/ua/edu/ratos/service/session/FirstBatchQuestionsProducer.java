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
public class FirstBatchQuestionsProducer {

    private final BatchQuestionsTransformAndShuffleService batchQuestionsTransformAndShuffleService;

    public List<QuestionSessionOutDto> getBatchQuestions(@NonNull final SessionData sessionData) {
        if (!sessionData.hasMoreQuestions()) throw new IllegalStateException("Wrong API usage: no more questions");
        if (sessionData.getCurrentIndex() !=0) throw new RuntimeException("Wrong API usage: non-zero currentIndex!");
        final List<QuestionDomain> sequence = sessionData.getSequence();
        final int size = sequence.size();
        final int questionsPerBatch = sessionData.getSchemeDomain().getSettingsDomain().getQuestionsPerSheet();
        List<QuestionDomain> result;
        if (questionsPerBatch<=0 || questionsPerBatch>=size) {
            // All questions per batch!
            result = new ArrayList<>(sequence);
        }  else {
            result = new ArrayList<>(sequence.subList(0, questionsPerBatch));
        }
        List<QuestionSessionOutDto> collect = batchQuestionsTransformAndShuffleService.transformAndShuffle(result);
        return collect;
    }

}
