package ua.edu.ratos.service.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.service.AbstractFactory;

import java.util.List;

@Component
public class FinishProcessingFactory extends AbstractFactory<String, FinishProcessingService> {

    @Autowired
    FinishProcessingFactory(List<FinishProcessingService> list) {
        super(list);
    }
}
