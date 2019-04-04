package ua.edu.ratos.service.session.sequence;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.service.QuestionService;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class CachedQuestionLoaderImpl extends AbstractQuestionLoader {


    private QuestionService questionService;

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public Map<Affiliation, Set<Question>> loadAllQuestionsToMap(@NonNull final Scheme scheme) {
        return super.loadAllQuestionsToMap(scheme);
    }

    @Override
    protected Set<Question> getTypeSet(@NonNull final Long themeId, @NonNull final Long typeId, byte level) {
        if (typeId.equals(1L)) return new HashSet<>(questionService.findAllMCQForCachedSessionByThemeId(themeId, level));
        if (typeId.equals(2L)) return new HashSet<>(questionService.findAllFBSQForCachedSessionByThemeId(themeId, level));
        if (typeId.equals(3L)) return new HashSet<>(questionService.findAllFBMQForCachedSessionByThemeId(themeId, level));
        if (typeId.equals(4L)) return new HashSet<>(questionService.findAllMQForCachedSessionByThemeId(themeId, level));
        if (typeId.equals(5L)) return new HashSet<>(questionService.findAllSQForCachedSessionByThemeId(themeId, level));
        throw new UnsupportedOperationException("Wrong type");
    }

    @Override
    public String getType() {
        return "cached";
    }
}
