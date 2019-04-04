package ua.edu.ratos.service.session.sequence;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.service.QuestionService;

import java.util.Set;

@Slf4j
@Service
public class SimpleQuestionLoaderImpl extends AbstractQuestionLoader {

    private QuestionService questionService;

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    protected Set<Question> getTypeSet(@NonNull final Long themeId, @NonNull final Long typeId, byte level) {
        return questionService.findAllForSessionByThemeIdAndType(themeId, typeId, level);
    }

    @Override
    public String getType() {
        return "simple";
    }
}
