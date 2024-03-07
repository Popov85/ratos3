package ua.edu.ratos.service.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.service.AbstractFactory;

import java.util.List;

@Component
public class NextProcessingFactory extends AbstractFactory<String, NextProcessingService> {

    @Autowired
    NextProcessingFactory(List<NextProcessingService> list) {
        super(list);
    }
}
