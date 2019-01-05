package ua.edu.ratos.service.session.sequence;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.SchemeThemeSettings;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.QuestionService;
import ua.edu.ratos.service.utils.CollectionShuffler;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Helper class that
 * serves the purpose of extracting questions from DB for learning session on a given Scheme's Theme;
 * Provides 2 interface methods to gather all found questions into either list or map
 */
@Slf4j
@Service
public class SequenceMapper {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CollectionShuffler collectionShuffler;

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
    public Map<Long, Map<Byte, List<QuestionDomain>>> getMap(Long themeId, Set<SchemeThemeSettings> settings) {
        Map<Long, Map<Byte, List<QuestionDomain>>> result = new HashMap<>(5);
        for (SchemeThemeSettings setting : settings) {
            final Long typeId = setting.getType().getTypeId();
            final Set<? extends QuestionDomain> typeList = questionService.findAllByThemeIdAndTypeId(themeId, typeId);
            if (typeList != null && !typeList.isEmpty()) {
                final short quantityLevel1 = setting.getLevel1();
                final short quantityLevel2 = setting.getLevel2();
                final short quantityLevel3 = setting.getLevel3();
                // Select all existing of this type L1, L2, L3 if not 0
                Map<Byte, List<QuestionDomain>> levelMap = new HashMap<>(3);
                if (quantityLevel1 != 0) {
                    levelMap.put((byte) 1, getPart(typeList, (byte) 1, quantityLevel1));
                }
                if (quantityLevel2 != 0) {
                    levelMap.put((byte) 2, getPart(typeList, (byte) 2, quantityLevel2));
                }
                if (quantityLevel3 != 0) {
                    levelMap.put((byte) 3, getPart(typeList, (byte) 3, quantityLevel3));
                }
                result.put(typeId, levelMap);
            }
        }
        return result;
    }

    private List<QuestionDomain> getPart(Set<? extends QuestionDomain> typeList, byte level, short quantity) {
        List<QuestionDomain> levelList = getLevelList(typeList, level);
        if (levelList.isEmpty()) return Collections.emptyList();
        // if the actual list is less than is requested, return a reduced number of questions
        if (quantity > levelList.size()) quantity = (short) levelList.size();
        log.debug("level = {}, levelList size = {}, requested quantity = {}", level, levelList.size(), quantity);
        return collectionShuffler.shuffle(levelList, quantity);
    }

    private List<QuestionDomain> getLevelList(Set<? extends QuestionDomain> questions, byte level) {
        final List<QuestionDomain> levelList = questions
                .stream()
                .filter(q -> q.getLevel()==level)
                .collect(Collectors.toList());
        return levelList;
    }

}
