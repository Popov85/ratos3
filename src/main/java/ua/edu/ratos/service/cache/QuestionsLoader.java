package ua.edu.ratos.service.cache;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.entity.SchemeTheme;
import ua.edu.ratos.dao.entity.SchemeThemeSettings;
import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.service.QuestionService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
class QuestionsLoader {

    private final QuestionService questionService;

    @Autowired
    QuestionsLoader(QuestionService questionService) {
        this.questionService = questionService;
    }

    void loadThemes(@NonNull final Scheme scheme) {
        List<SchemeTheme> schemeThemes = scheme.getThemes();
        for (SchemeTheme schemeTheme : schemeThemes) {
            Long themeId = schemeTheme.getTheme().getThemeId();
            Set<SchemeThemeSettings> settings = schemeTheme.getSettings();
            for (SchemeThemeSettings setting : settings) {
                Long typeId = setting.getType().getTypeId();
                // For each out of 3 levels
                short level1 = setting.getLevel1();
                if (level1 !=0) {
                    Set<Question> typeSet = getTypeSet(themeId, typeId, (byte) 1);
                    log.debug("Loaded all questions for schemeId = {},  themeId = {}, typeId = {}, level = {} quantity = {}",
                            scheme.getSchemeId(), themeId, typeId, 1, typeSet.size());
                }
                short level2 = setting.getLevel2();
                if (level2 !=0) {
                    Set<Question> typeSet = getTypeSet(themeId, typeId, (byte) 2);
                    log.debug("Loaded all questions for schemeId = {},  themeId = {}, typeId = {}, level = {} quantity = {}",
                            scheme.getSchemeId(), themeId, typeId, 2, typeSet.size());
                }
                short level3 = setting.getLevel3();
                if (level3 !=0) {
                    Set<Question> typeSet = getTypeSet(themeId, typeId, (byte) 3);
                    log.debug("Loaded all questions for schemeId = {}, themeId = {}, typeId = {}, level = {} quantity = {}",
                            scheme.getSchemeId(), themeId, typeId, 3, typeSet.size());
                }
            }
        }
    }

    private Set<Question> getTypeSet(@NonNull final Long themeId, @NonNull final Long typeId, byte level) {
        if (typeId.equals(1L)) return new HashSet<>(questionService.findAllMCQForCacheUpdateByThemeId(themeId, level));
        if (typeId.equals(2L)) return new HashSet<>(questionService.findAllFBSQForCacheUpdateByThemeId(themeId, level));
        if (typeId.equals(3L)) return new HashSet<>(questionService.findAllFBMQForCacheUpdateByThemeId(themeId, level));
        if (typeId.equals(4L)) return new HashSet<>(questionService.findAllMQForCacheUpdateByThemeId(themeId, level));
        if (typeId.equals(5L)) return new HashSet<>(questionService.findAllSQForCacheUpdateByThemeId(themeId, level));
        throw new UnsupportedOperationException("Wrong type, typeId = "+typeId);
    }
}
