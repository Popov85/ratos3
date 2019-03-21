package ua.edu.ratos.service.session.sequence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.SchemeTheme;
import ua.edu.ratos.dao.entity.SchemeThemeSettings;
import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.service.utils.CollectionShuffler;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Globally randomized strategy:
 * Theme -> Type -> Level -> Question
 * Questions are randomised irrespective to questionType, level and themes
 */
@Service
public class SequenceProducerRandomImpl implements SequenceProducer{

    private CollectionShuffler collectionShuffler;

    @Autowired
    public void setCollectionShuffler(CollectionShuffler collectionShuffler) {
        this.collectionShuffler = collectionShuffler;
    }

    @Override
    public List<Question> getSequence(List<SchemeTheme> schemeThemes, SequenceMapper sequenceMapper) {
        List<Question> result = new ArrayList<>();
        for (SchemeTheme schemeTheme : schemeThemes) {
            final Long themeId = schemeTheme.getTheme().getThemeId();
            final Set<SchemeThemeSettings> settings = schemeTheme.getSettings();
            final List<Question> themeResult = sequenceMapper.getNOutOfM(themeId, settings);
            result.addAll(themeResult);
        }
        // Shuffle all scheme's questions
        return collectionShuffler.shuffle(result);
    }

    @Override
    public String type() {
        return "random";
    }
}
