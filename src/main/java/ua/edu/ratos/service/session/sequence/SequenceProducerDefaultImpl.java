package ua.edu.ratos.service.session.sequence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.SchemeTheme;
import ua.edu.ratos.dao.entity.SchemeThemeSettings;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.utils.CollectionShuffler;
import java.util.*;

/**
 * Default strategy:
 * Theme-> Type -> Level -> Question
 * Questions are randomised irrespective to type and level, but observe themes order
 */
@Service
public class SequenceProducerDefaultImpl implements SequenceProducer {

    @Autowired
    private SequenceMapper sequenceMapper;

    @Autowired
    private CollectionShuffler collectionShuffler;

    @Override
    public List<QuestionDomain> getSequence(List<SchemeTheme> schemeThemes) {
        List<QuestionDomain> result = new ArrayList<>();
        for (SchemeTheme schemeTheme : schemeThemes) {
            final Long themeId = schemeTheme.getTheme().getThemeId();
            final Set<SchemeThemeSettings> settings = schemeTheme.getSchemeThemeSettings();
            final List<QuestionDomain> themeResult = sequenceMapper.getList(themeId, settings);
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
