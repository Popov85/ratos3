package ua.edu.ratos.service.session.sequence;

import ua.edu.ratos.dao.entity.SchemeTheme;
import ua.edu.ratos.service.domain.question.QuestionDomain;

import java.util.List;

/**
 * Produces a list of Questions according to the selected strategy from SchemeDomain's settings and wanted quantity of questions from SchemeDomain's Themes list
 */
public interface SequenceProducer {
    List<QuestionDomain> getSequence(List<SchemeTheme> schemeThemes);
    String type();
}
