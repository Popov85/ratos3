package ua.edu.ratos.service.session.sequence;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.service.utils.CollectionShuffler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Default strategy:
 * Questions are randomised irrespective to question type and level, but observe themes order
 */
@Slf4j
@Service
@AllArgsConstructor
public class SequenceProducerDefaultImpl implements SequenceProducer {

    private final SubSetProducer subSetProducer;

    private final CollectionShuffler collectionShuffler;

    @Override
    public List<Question> getSequence(@NonNull final Scheme scheme, @NonNull final QuestionLoader questionLoader) {
        // If mapped - load map then continue
        List<Question> result = new ArrayList<>();

        // keep themes order
        List<Long> themes = scheme.getThemes().stream().map(t -> t.getTheme().getThemeId()).collect(Collectors.toList());

        // load all questions, preferably from cache, can be a lot (~10.000)
        Map<Affiliation, Set<Question>> map = questionLoader.loadAllQuestionsToMap(scheme);
        // Select a subset according to settings
        List<Question> questions = subSetProducer.getSubSet(map);

        // Split by themes
        Map<Long, List<Question>> byTheme = questions.stream().collect(Collectors.groupingBy(q->q.getTheme().getThemeId(), Collectors.toList()));

        for (Long theme : themes) {
            List<Question> themeQuestions = byTheme.get(theme);
            if (themeQuestions!=null) result.addAll(collectionShuffler.shuffle(themeQuestions));
        }
        return result;
    }

    @Override
    public String name() {
        return "default";
    }

}
