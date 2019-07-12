package ua.edu.ratos.service.session.grade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.service.AbstractFactory;

import java.util.List;

@Component
public class GradingFactory extends AbstractFactory<String, Grader> {

    @Autowired
    GradingFactory(List<Grader> list) {
        super(list);
    }
}
