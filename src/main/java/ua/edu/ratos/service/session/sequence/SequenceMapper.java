package ua.edu.ratos.service.session.sequence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.SchemeThemeSettings;
import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.service.QuestionService;
import ua.edu.ratos.service.utils.CollectionShuffler;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Helper class that
 * serves the purpose of extracting questions from DB for learning dto on a given Scheme's Theme;
 * Provides 2 interface methods to gather all found questions into either list or map
 */
@Service
public class SequenceMapper {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CollectionShuffler collectionShuffler;

    /**
     * Gathers all Theme's questions into list
     * Extract all existing in a DB questions for each Theme
     * Randomly reduce the size of collections of questions of each level according to settings and put to list
     * @param themeId theme from Scheme's Themes list
     * @param settings settings from SchemeTheme's list
     * @return list of all questions for a certain Theme
     */
    public List<Question> getList(Long themeId, Set<SchemeThemeSettings> settings) {
        List<Question> themeResult = new ArrayList<>();
        for (SchemeThemeSettings setting : settings) {
            final Long typeId = setting.getType().getTypeId();
            final Set<? extends Question> typeList = questionService.findAllByThemeIdAndTypeId(themeId, typeId);
            final short quantityLevel1 = setting.getLevel1();
            final short quantityLevel2 = setting.getLevel2();
            final short quantityLevel3 = setting.getLevel3();
            // Select all existing of this type L1, L2, L3 if not 0
            if (quantityLevel1!=0) {
                themeResult.addAll(getPart(typeList, (byte) 1, quantityLevel1));
            }
            if (quantityLevel2!=0) {
                themeResult.addAll(getPart(typeList, (byte) 2, quantityLevel2));
            }
            if (quantityLevel3!=0) {
                themeResult.addAll(getPart(typeList, (byte) 3, quantityLevel3));
            }
        }
        return themeResult;
    }


    /**
     * Gathers all Theme's questions into map
     * Extract all existing in a DB questions for each Theme
     * Randomly reduce the size of collections of questions of each level according to settings and put to map
     * Map<Type, Map<Level, List<Question>>>
     * @param themeId theme from Scheme's Themes list
     * @param settings settings from SchemeTheme's list
     * @return map of questions by type and by level within type
     */
    public Map<Long, Map<Byte, List<Question>>> getMap(Long themeId, Set<SchemeThemeSettings> settings) {
        Map<Long, Map<Byte, List<Question>>> result = new HashMap<>(5);
        for (SchemeThemeSettings setting : settings) {
            final Long typeId = setting.getType().getTypeId();
            final Set<? extends Question> typeList = questionService.findAllByThemeIdAndTypeId(themeId, typeId);
            final short quantityLevel1 = setting.getLevel1();
            final short quantityLevel2 = setting.getLevel2();
            final short quantityLevel3 = setting.getLevel3();
            // Select all existing of this type L1, L2, L3 if not 0
            Map<Byte, List<Question>> levelMap = new HashMap<>(3);
            if (quantityLevel1!=0) {
                levelMap.put((byte)1, getPart(typeList, (byte) 1, quantityLevel1));
            }
            if (quantityLevel2!=0) {
                levelMap.put((byte)2, getPart(typeList, (byte) 2, quantityLevel2));
            }
            if (quantityLevel3!=0) {
                levelMap.put((byte)3, getPart(typeList, (byte) 3, quantityLevel3));
            }
            result.put(typeId, levelMap);
        }
        return result;
    }

    private List<Question> getPart(Set<? extends Question> typeList, byte level, short quantity) {
        return collectionShuffler.shuffle(getLevelList(typeList, level), quantity);
    }

    private List<Question> getLevelList(Set<? extends Question> questions, byte level) {
        final List<Question> levelList = questions
                .stream()
                .filter(q -> q.getLevel()==level)
                .collect(Collectors.toList());
        return levelList;
    }

}
