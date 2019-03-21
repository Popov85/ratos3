package ua.edu.ratos.service.session.sequence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.config.TrackTime;
import ua.edu.ratos.dao.entity.SchemeTheme;
import ua.edu.ratos.dao.entity.SchemeThemeSettings;
import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.service.utils.CollectionShuffler;
import java.util.*;

/**
 * Default strategy:
 * Theme-> Type -> Level -> Question
 * Questions are randomised irrespective to questionType and level, but observe themes order
 */
@Service
public class SequenceProducerDefaultImpl implements SequenceProducer {

    private CollectionShuffler collectionShuffler;

    @Autowired
    public void setCollectionShuffler(CollectionShuffler collectionShuffler) {
        this.collectionShuffler = collectionShuffler;
    }

    @TrackTime
    @Override
    public List<Question> getSequence(List<SchemeTheme> schemeThemes, SequenceMapper sequenceMapper) {
        List<Question> result = new ArrayList<>();
        for (SchemeTheme schemeTheme : schemeThemes) {
            final Long themeId = schemeTheme.getTheme().getThemeId();
            final Set<SchemeThemeSettings> settings = schemeTheme.getSettings();
            final List<Question> themeResult = sequenceMapper.getNOutOfM(themeId, settings);
            // Shuffle all theme's questions before adding to the result
            result.addAll(collectionShuffler.shuffle(themeResult));
        }
        return result;
    }

    @Override
    public String type() {
        return "default";
    }

}
