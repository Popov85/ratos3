package ua.edu.ratos.service.session.sequence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.domain.entity.SchemeTheme;
import ua.edu.ratos.domain.entity.SchemeThemeSettings;
import ua.edu.ratos.domain.entity.question.Question;
import ua.edu.ratos.service.utils.CollectionShuffler;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Globally randomized strategy:
 * Theme -> Type -> Level -> Question
 * Questions are randomised irrespective to type, level and themes
 */
@Service
public class SequenceProducerRandomImpl implements SequenceProducer{

    @Autowired
    private SequenceMapper sequenceMapper;

    @Autowired
    private CollectionShuffler collectionShuffler;

    @Override
    public List<Question> getSequence(List<SchemeTheme> schemeThemes) {
        List<Question> result = new ArrayList<>();
        for (SchemeTheme schemeTheme : schemeThemes) {
            final Long themeId = schemeTheme.getTheme().getThemeId();
            final Set<SchemeThemeSettings> settings = schemeTheme.getSchemeThemeSettings();
            final List<Question> themeResult = sequenceMapper.getList(themeId, settings);
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
