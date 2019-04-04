package ua.edu.ratos.service.session.sequence;

import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.entity.question.Question;
import java.util.List;

/**
 * Produces a personal list of questions according scheme's settings.
 */
public interface SequenceProducer {
    List<Question> getSequence(Scheme scheme, QuestionLoader questionLoader);
    String getStrategy();
}
