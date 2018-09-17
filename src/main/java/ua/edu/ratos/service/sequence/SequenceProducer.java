package ua.edu.ratos.service.sequence;

import ua.edu.ratos.domain.entity.SchemeTheme;
import ua.edu.ratos.domain.entity.question.Question;
import java.util.List;

/**
 * Produces a list of Questions according to the selected strategy from Scheme's settings and wanted quantity of questions from Scheme's Themes list
 */
public interface SequenceProducer {
    List<Question> getSequence(List<SchemeTheme> schemeThemes);
}
