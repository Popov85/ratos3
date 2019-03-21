package ua.edu.ratos.service.session.sequence;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.SchemeThemeSettings;
import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.dao.repository.QuestionRepository;
import ua.edu.ratos.service.utils.CollectionShuffler;

import java.util.*;

/**
 * This implementation is database specific, so it may not work with a different production DBMS.
 * List of currently supported DBMSs {MySql}
 */
@Slf4j
@Service
public class SequenceMapperDBSpecificImpl implements SequenceMapper {

    private QuestionRepository questionRepository;

    private CollectionShuffler collectionShuffler;

    @Autowired
    public void setQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Autowired
    public void setCollectionShuffler(CollectionShuffler collectionShuffler) {
        this.collectionShuffler = collectionShuffler;
    }

    public List<Question> getNOutOfM(Long themeId, Set<SchemeThemeSettings> settings) {
        List<Question> themeResult = new ArrayList<>();
        for (SchemeThemeSettings setting : settings) {
            final Long typeId = setting.getType().getTypeId();
            final short quantityLevel1 = setting.getLevel1();
            final short quantityLevel2 = setting.getLevel2();
            final short quantityLevel3 = setting.getLevel3();
            // Select all existing of this questionType L1, L2, L3 if not 0
            if (quantityLevel1 != 0) {
                Collection<Question> questions = getLevelPart(themeId, typeId, (byte) 1, quantityLevel1);
                themeResult.addAll(questions);
            }
            if (quantityLevel2 != 0) {
                Collection<Question> questions = getLevelPart(themeId, typeId, (byte) 2, quantityLevel2);
                themeResult.addAll(questions);
            }
            if (quantityLevel3 != 0) {
                Collection<Question> questions = getLevelPart(themeId, typeId, (byte) 3, quantityLevel3);
                themeResult.addAll(questions);
            }
        }
        return themeResult;
    }

    private Collection<Question> getLevelPart(Long themeId, Long typeId, byte level, int quantity) {
        Set<Question> required = questionRepository.findAllRequiredForSessionByThemeIdAndTypeAndLevel(themeId, typeId, level);
        if (required.isEmpty()) {
            Set<Question> subset = questionRepository.findNOutOfMForSessionByThemeIdAndTypeAndLevel(themeId, typeId, level, quantity);
            log.debug("Selected only none-required questions for theme = {}, of type = {}, level = {}, quantity = {}", themeId, typeId, level, quantity);
            return subset;
        } else {// there are some required questions present
            // Case 1) Required is more than enough or exactly enough;
            if (required.size()>=quantity) {
                // Fetch random sublist
                List<Question> sublist = collectionShuffler.shuffle(required, quantity);
                log.debug("Selected only required questions for theme = {}, of type = {}, level = {}, quantity = {}", themeId, typeId, level, quantity);
                return sublist;
            } else { // Case 2) Most common, required is not enough so add some none-required questions;
                // Get all required
                Set<Question> subset = new HashSet<>(required);
                int noneRequiredQuantity = quantity - required.size();
                // Plus add some none-required
                Set<Question> noneRequired = questionRepository.findNOutOfMForSessionByThemeIdAndTypeAndLevel(themeId, typeId, level, noneRequiredQuantity);
                subset.addAll(noneRequired);
                log.debug("Selected mixed questions for theme = {}, of type = {}, level = {}, required = {}, none-required = {}, ", themeId, typeId, level, required.size(), noneRequired.size());
                return subset;
            }
        }
    }

    @Override
    public String type() {
        return "db";
    }
}
