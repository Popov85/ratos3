package ua.edu.ratos.service.session.sequence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.service.AbstractFactory;

import java.util.List;


@Component
public class QuestionLoaderFactory extends AbstractFactory<String, QuestionLoader> {

    @Autowired
    QuestionLoaderFactory(List<QuestionLoader> list) {
        super(list);
    }
}
