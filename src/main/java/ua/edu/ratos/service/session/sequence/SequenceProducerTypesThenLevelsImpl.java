package ua.edu.ratos.service.session.sequence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.SchemeTheme;
import ua.edu.ratos.service.domain.question.QuestionDomain;
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

    private SequenceMapper sequenceMapper;

    private CollectionShuffler collectionShuffler;

    @Autowired
    public void setSequenceMapper(SequenceMapper sequenceMapper) {
        this.sequenceMapper = sequenceMapper;
    }

    @Autowired
    public void setCollectionShuffler(CollectionShuffler collectionShuffler) {
        this.collectionShuffler = collectionShuffler;
    }

    @Override
    public List<QuestionDomain> getSequence(List<SchemeTheme> schemeThemes) {
        List<QuestionDomain> result = new ArrayList<>();
        schemeThemes.forEach(s-> result.addAll(sequenceMapper.getList(s.getTheme().getThemeId(), s.getSettings())));
        // Shuffle and sort according to patterns
        List<QuestionDomain> resultType = new ArrayList<>();
        TYPE_PATTERN.forEach(typeId->{
            final List<QuestionDomain> byType = result.stream().filter(q -> q.getType() == typeId).collect(Collectors.toList());
            if (!byType.isEmpty()) {
                LEVEL_PATTERN.forEach(l -> {
                    final List<QuestionDomain> byLevel = byType.stream().filter(q -> q.getLevel() == l).collect(Collectors.toList());
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
