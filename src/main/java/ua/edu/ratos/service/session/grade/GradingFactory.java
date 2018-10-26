package ua.edu.ratos.service.session.grade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GradingFactory {

    @Autowired
    private List<Grader> graders;

    private final Map<String, Grader> gradersMap = new HashMap<>();

    @PostConstruct
    public void init() {
        for(Grader grader : graders) {
            gradersMap.put(grader.type(), grader);
        }
    }

    public Grader getGrader(String type) {
        Grader grader = gradersMap.get(type);
        if(grader == null) throw new RuntimeException("Unknown grader type: " + type);
        return grader;
    }
}
