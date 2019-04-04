package ua.edu.ratos.service.session.sequence;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.entity.SchemeTheme;
import ua.edu.ratos.dao.entity.SchemeThemeSettings;
import ua.edu.ratos.dao.entity.question.Question;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public abstract class AbstractQuestionLoader implements QuestionLoader {

    @Override
    public Map<Affiliation, Set<Question>> loadAllQuestionsToMap(@NonNull final Scheme scheme) {
        Map<Affiliation, Set<Question>> result = new HashMap<>();
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
                    log.debug("Loaded all questions for themeId = {}, typeId = {}, level = {} quantity = {}", themeId, typeId, 1, typeSet.size());
                    result.put(new Affiliation(themeId, typeId, (byte) 1, level1), typeSet);
                }
                short level2 = setting.getLevel2();
                if (level2 !=0) {
                    Set<Question> typeSet = getTypeSet(themeId, typeId, (byte) 2);
                    log.debug("Loaded all questions for themeId = {}, typeId = {}, level = {} quantity = {}", themeId, typeId, 2, typeSet.size());
                    result.put(new Affiliation(themeId, typeId, (byte) 2, level2), typeSet);
                }
                short level3 = setting.getLevel3();
                if (level3 !=0) {
                    Set<Question> typeSet = getTypeSet(themeId, typeId, (byte) 3);
                    log.debug("Loaded all questions for themeId = {}, typeId = {}, level = {} quantity = {}", themeId, typeId, 3, typeSet.size());
                    result.put(new Affiliation(themeId, typeId, (byte) 3, level3), typeSet);
                }
            }
        }
        return result;
    }

    protected abstract Set<Question> getTypeSet(Long themeId, Long typeId, byte level);
}
