package ua.edu.ratos.service.session.sequence;

import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.service.NamedService;

import java.util.List;

/**
 * Produces a personal list of questions according scheme's settings.
 */
public interface SequenceProducer extends NamedService<String> {

    List<Question> getSequence(Scheme scheme, QuestionLoader questionLoader);

}
