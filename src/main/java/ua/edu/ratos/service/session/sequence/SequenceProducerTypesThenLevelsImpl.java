package ua.edu.ratos.service.session.sequence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.domain.entity.SchemeTheme;
import ua.edu.ratos.domain.entity.question.Question;
import ua.edu.ratos.service.utils.CollectionShuffler;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Strategy TypeThenLevel that provides questions in order of types then levels {MCQ} {1, 2, 3}, {FBSQ} {1, 2, 3}, etc.
 * according to TYPE and LEVEL pattern constants, Theme's order is ignored
 */
@Service
public class SequenceProducerTypesThenLevelsImpl implements SequenceProducer {

    private static final List<Long> TYPE_PATTERN = Arrays.asList(1L, 2L, 3L, 4L, 5L);

    private static final List<Byte> LEVEL_PATTERN = Arrays.asList((byte) 1, (byte) 2, (byte) 3);

    @Autowired
    private SequenceMapper sequenceMapper;

    @Autowired
    private CollectionShuffler collectionShuffler;

    @Override
    public List<Question> getSequence(List<SchemeTheme> schemeThemes) {
        List<Question> result = new ArrayList<>();
        schemeThemes.forEach(s-> result.addAll(sequenceMapper.getList(s.getTheme().getThemeId(), s.getSchemeThemeSettings())));
        // Shuffle and sort according to patterns
        List<Question> resultType = new ArrayList<>();
        TYPE_PATTERN.forEach(typeId->{
            final List<Question> byType = result.stream().filter(q -> q.getType().getTypeId() == typeId).collect(Collectors.toList());
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
    public String type() {
        return "types&levels";
    }
}
