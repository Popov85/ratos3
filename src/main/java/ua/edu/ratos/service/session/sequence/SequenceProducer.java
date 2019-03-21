package ua.edu.ratos.service.session.sequence;

import ua.edu.ratos.dao.entity.SchemeTheme;
import ua.edu.ratos.dao.entity.question.Question;
import java.util.List;

/**
 * Produces a list of Questions according to the selected strategy from SchemeDomain's settings and wanted quantity of questions from SchemeDomain's Themes list
 */
public interface SequenceProducer {
    List<Question> getSequence(List<SchemeTheme> schemeThemes, SequenceMapper sequenceMapper);
    String type();
}
