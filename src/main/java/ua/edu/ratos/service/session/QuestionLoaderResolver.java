package ua.edu.ratos.service.session;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.entity.SchemeTheme;
import ua.edu.ratos.dao.repository.QuestionRepository;
import ua.edu.ratos.service.session.sequence.QuestionLoader;
import ua.edu.ratos.service.session.sequence.QuestionLoaderFactory;

import java.util.List;

@Slf4j
@Service
public class QuestionLoaderResolver {

    private QuestionRepository questionRepository;

    private QuestionLoaderFactory sequenceMapperFactory;

    @Autowired
    public void setQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Autowired
    public void setSequenceMapperFactory(QuestionLoaderFactory sequenceMapperFactory) {
        this.sequenceMapperFactory = sequenceMapperFactory;
    }

    /**
     * Algorithm to decide which implementation to use
     * @param scheme
     * @return appropriate QuestionLoader implementation
     */
    public QuestionLoader resolve(@NonNull final Scheme scheme) {
        List<SchemeTheme> themes = scheme.getThemes();
        if (themes.isEmpty()) throw new IllegalStateException("Scheme with no themes detected!");
        if (themes.size()==1) {
            Long themeId = themes.get(0).getTheme().getThemeId();
            int quantity = questionRepository.countByThemeId(themeId);
            if (quantity <=100) {
                log.debug("Detected single-theme scheme with a few questions = {}, choose simple implementation of question loader", quantity);
                return sequenceMapperFactory.getInstance("simple");
            } else {
                log.debug("Detected single-theme scheme with a lot of questions = {}, choose cached implementation of question loader", quantity);
                return sequenceMapperFactory.getInstance("cached");
            }
        } else {
            log.debug("Detected complex scheme, themes = {}, choose thread-limited implementation of question loader", themes.size());
            return sequenceMapperFactory.getInstance("thread-limited");
        }
    }
}
