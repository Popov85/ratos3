package ua.edu.ratos.service.session.sequence;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.SchemeThemeSettings;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.QuestionService;
import java.util.*;

/**
 * Helper class that
 * serves the purpose of extracting questions from DB for learning session on a given Scheme's Theme;
 * Provides 2 interface methods to gather all found questions into either list or map
 */
@Slf4j
@Service
public class SequenceMapper {

    private QuestionService questionService;

    private LevelPartProducer levelPartProducer;

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Autowired
    public void setLevelPartProducer(LevelPartProducer levelPartProducer) {
        this.levelPartProducer = levelPartProducer;
    }

    /**
     * Gathers all Theme's questions into a single list
     * Extract all existing in a DB questions for each Theme
     * Randomly reduce the size of collections of questions of each level according to settings and put to list
     * @param themeId theme from Scheme's Themes list
     * @param settings settings from SchemeTheme's list
     * @return list of all questions for a certain Theme
     */
    public List<QuestionDomain> getList(Long themeId, Set<SchemeThemeSettings> settings) {
        List<QuestionDomain> themeResult = new ArrayList<>();
        for (SchemeThemeSettings setting : settings) {
            final Long typeId = setting.getType().getTypeId();
            final Set<? extends QuestionDomain> typeList = questionService.findAllByThemeIdAndTypeId(themeId, typeId);
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


    /**
     * Gathers all Theme's questions into map.
     * Extracts all existing in a DB questions for each Theme.
     * Randomly reduces the size of collections of questions of each level according to settings and put to map
     * Map<Type, Map<Level, List<Question>>>
     * @param themeId theme from Scheme's Themes list
     * @param settings settings from SchemeTheme's list
     * @return map of questions by questionType and by level within questionType
     */
    public Map<Long, Map<Byte, List<QuestionDomain>>> getMap(Long themeId, Set<SchemeThemeSettings> settings) {
        Map<Long, Map<Byte, List<QuestionDomain>>> result = new HashMap<>(5);
        for (SchemeThemeSettings setting : settings) {
            final Long typeId = setting.getType().getTypeId();
            final Set<? extends QuestionDomain> typeList = questionService.findAllByThemeIdAndTypeId(themeId, typeId);
            if (typeList != null && !typeList.isEmpty()) {
                final short quantityLevel1 = setting.getLevel1();
                final short quantityLevel2 = setting.getLevel2();
                final short quantityLevel3 = setting.getLevel3();
                // Select all existing of this questionType L1, L2, L3 if not 0
                Map<Byte, List<QuestionDomain>> levelMap = new HashMap<>(3);
                if (quantityLevel1 != 0) {
                    levelMap.put((byte) 1, levelPartProducer.getLevelPart(typeList, (byte) 1, quantityLevel1));
                }
                if (quantityLevel2 != 0) {
                    levelMap.put((byte) 2, levelPartProducer.getLevelPart(typeList, (byte) 2, quantityLevel2));
                }
                if (quantityLevel3 != 0) {
                    levelMap.put((byte) 3, levelPartProducer.getLevelPart(typeList, (byte) 3, quantityLevel3));
                }
                result.put(typeId, levelMap);
            }
        }
        return result;
    }

}
