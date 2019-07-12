package ua.edu.ratos.service.session.sequence;

import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.service.NamedService;

import java.util.Map;
import java.util.Set;

public interface QuestionLoader extends NamedService<String> {

    Map<Affiliation, Set<Question>> loadAllQuestionsToMap(Scheme scheme);
}
