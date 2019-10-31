package ua.edu.ratos.service.session.sequence;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.service.QuestionService;

import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class SimpleQuestionLoaderImpl extends AbstractQuestionLoader {

    private final QuestionService questionService;

    @Override
    protected Set<Question> getTypeSet(@NonNull final Long themeId, @NonNull final Long typeId, byte level) {
        return questionService.findAllForSessionByThemeIdAndType(themeId, typeId, level);
    }

    @Override
    public String name() {
        return "simple";
    }
}
