package ua.edu.ratos.service.session.sequence;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.SchemeThemeSettings;
import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.dao.repository.QuestionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This implementation is slower but universal, works satisfactory with all possible databases via JPA specification,
 * with average table sizes. Heavily utilizes Hibernate's L2C. The more questions+answers in L2C, the faster it works.
 */
@Slf4j
@Service
public class SequenceMapperJPAUniversalImpl implements SequenceMapper {

    private QuestionRepository questionRepository;

    private LevelPartProducer levelPartProducer;

    @Autowired
    public void setQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Autowired
    public void setLevelPartProducer(LevelPartProducer levelPartProducer) {
        this.levelPartProducer = levelPartProducer;
    }

    @Override
    public List<Question> getNOutOfM(Long themeId, Set<SchemeThemeSettings> settings) {
        List<Question> themeResult = new ArrayList<>();
        for (SchemeThemeSettings setting : settings) {
            final Long typeId = setting.getType().getTypeId();

            Set<Question> typeList = questionRepository.findAllForSessionByThemeIdAndType(themeId, typeId);
            log.debug("Selected all questions for themeId = {}, of typeId = {}, total = {}", themeId, typeId, typeList.size());

            if (typeList != null && !typeList.isEmpty()) {
                final short quantityLevel1 = setting.getLevel1();
                final short quantityLevel2 = setting.getLevel2();
                final short quantityLevel3 = setting.getLevel3();
                // Select all existing of this questionType L1, L2, L3 if not 0
                if (quantityLevel1!=0) {
                    themeResult.addAll(levelPartProducer.getLevelPart(typeList, (byte) 1, quantityLevel1));
                }
                if (quantityLevel2!=0) {
                    themeResult.addAll(levelPartProducer.getLevelPart(typeList, (byte) 2, quantityLevel2));
                }
                if (quantityLevel3!=0) {
                    themeResult.addAll(levelPartProducer.getLevelPart(typeList, (byte) 3, quantityLevel3));
                }
            }
        }
        return themeResult;
    }

    @Override
    public String type() {
        return "jpa";
    }

}
