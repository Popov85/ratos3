package ua.edu.ratos.service.session.sequence;

import ua.edu.ratos.dao.entity.SchemeThemeSettings;
import ua.edu.ratos.dao.entity.question.Question;

import java.util.List;
import java.util.Set;

/**
 * This interface serves the purpose of extracting questions from DB for learning session on a given Scheme's Theme;
 * Provides an interface method to gather all found questions into a list.
 */
public interface SequenceMapper {
    /**
     * According to Settings, selects randomly the required quantity of questions of needed type and level for the Theme
     * @param themeId themeId from Scheme's Themes list
     * @param settings settings from SchemeTheme's list
     * @return list of selected questions for a certain Theme
     */
    List<Question> getNOutOfM(Long themeId, Set<SchemeThemeSettings> settings);
    String type();
}
