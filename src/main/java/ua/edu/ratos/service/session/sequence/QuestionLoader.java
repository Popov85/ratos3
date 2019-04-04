package ua.edu.ratos.service.session.sequence;

import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.entity.question.Question;

import java.util.Map;
import java.util.Set;

public interface QuestionLoader {

    Map<Affiliation, Set<Question>> loadAllQuestionsToMap(Scheme scheme);

    String getType();
}
