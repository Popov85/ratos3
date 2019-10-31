package ua.edu.ratos.service.session.sequence;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.service.utils.CollectionShuffler;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Globally randomized strategy:
 * Theme -> Type -> Level -> Question
 * Questions are randomised irrespective to type, level and themes
 */
@Service
@AllArgsConstructor
public class SequenceProducerRandomImpl implements SequenceProducer {

    private final SubSetProducer subSetProducer;

    private final CollectionShuffler collectionShuffler;

    @Override
    public List<Question> getSequence(@NonNull final Scheme scheme, @NonNull final QuestionLoader questionLoader) {
        // load all questions, preferably from cache, can be a lot (10.000)
        Map<Affiliation, Set<Question>> map = questionLoader.loadAllQuestionsToMap(scheme);
        // Select a subset according to settings
        List<Question> result = subSetProducer.getSubSet(map);
        // Shuffle all scheme's questions
        return collectionShuffler.shuffle(result);
    }

    @Override
    public String name() {
        return "random";
    }
}
