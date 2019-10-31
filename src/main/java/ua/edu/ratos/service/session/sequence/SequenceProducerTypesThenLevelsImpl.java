package ua.edu.ratos.service.session.sequence;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.service.utils.CollectionShuffler;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Strategy TypeThenLevel that provides questions in order of types then levels {MCQ} {1, 2, 3}, {FBSQ} {1, 2, 3}, etc.
 * according to TYPE and LEVEL pattern constants, Theme's order is ignored
 */
@Service
@AllArgsConstructor
public class SequenceProducerTypesThenLevelsImpl implements SequenceProducer {

    private static final List<Long> TYPE_PATTERN = Arrays.asList(1L, 2L, 3L, 4L, 5L);
    private static final List<Byte> LEVEL_PATTERN = Arrays.asList((byte) 1, (byte) 2, (byte) 3);

    private final SubSetProducer subSetProducer;

    private final CollectionShuffler collectionShuffler;

    @Override
    public List<Question> getSequence(@NonNull final Scheme scheme, @NonNull final QuestionLoader questionLoader) {
        // load all questions, preferably from cache, can be a lot (10.000)
        Map<Affiliation, Set<Question>> map = questionLoader.loadAllQuestionsToMap(scheme);
        // Select a subset according to settings
        List<Question> result = subSetProducer.getSubSet(map);

        // Shuffle and sort according to patterns
        List<Question> resultType = new ArrayList<>();
        TYPE_PATTERN.forEach(typeId->{
            final List<Question> byType = result.stream().filter(q -> q.getType().getTypeId().equals(typeId)).collect(Collectors.toList());
            if (!byType.isEmpty()) {
                LEVEL_PATTERN.forEach(l -> {
                    final List<Question> byLevel = byType.stream().filter(q -> q.getLevel() == l).collect(Collectors.toList());
                    if (!byLevel.isEmpty()) resultType.addAll(collectionShuffler.shuffle(byLevel));
                });
            }
        });
        return resultType;
    }

    @Override
    public String name() {
        return "types&levels";
    }
}
