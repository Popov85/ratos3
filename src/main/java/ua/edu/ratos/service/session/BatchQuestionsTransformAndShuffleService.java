package ua.edu.ratos.service.session;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.edu.ratos.config.properties.AppProperties;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.dto.session.question.QuestionSessionOutDto;
import ua.edu.ratos.service.utils.CollectionShuffler;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class BatchQuestionsTransformAndShuffleService {

    private final AppProperties appProperties;

    private final CollectionShuffler collectionShuffler;

    public List<QuestionSessionOutDto> transformAndShuffle(List<QuestionDomain> result) {
        // Transform to session DTO form
        List<QuestionSessionOutDto> collect = result
                .stream()
                .map(q -> q.toDto())
                .collect(Collectors.toList());

        // shuffle answers if enabled and where appropriate
        if (appProperties.getSession()!=null && appProperties.getSession().isShuffleEnabled())
            collect.forEach(q->{ if (q.isShufflingSupported()) q.shuffle(collectionShuffler);});
        return collect;
    }
}
