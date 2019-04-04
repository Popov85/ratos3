package ua.edu.ratos.service.session.sequence;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class QuestionLoaderFactory {

    private List<QuestionLoader> questionLoaders;

    @Autowired
    public void setQuestionLoaders(List<QuestionLoader> questionLoaders) {
        this.questionLoaders = questionLoaders;
    }

    private Map<String, QuestionLoader> questionLoaderMap = new HashMap<>();

    @PostConstruct
    public void init() {
        for(QuestionLoader questionLoader : questionLoaders) {
            questionLoaderMap.put(questionLoader.getType(), questionLoader);
        }
    }

    public QuestionLoader getQuestionLoader(@NonNull final String type) {
        final QuestionLoader questionLoader = this.questionLoaderMap.get(type);
        if(questionLoader == null) throw new RuntimeException("Unknown question loader type: " + type);
        return questionLoader;
    }
}
