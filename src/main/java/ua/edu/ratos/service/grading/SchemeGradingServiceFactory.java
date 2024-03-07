package ua.edu.ratos.service.grading;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.service.AbstractFactory;

import java.util.List;

@Slf4j
@Component
public class SchemeGradingServiceFactory extends AbstractFactory<Long, SchemeGraderService> {

    @Autowired
    SchemeGradingServiceFactory(List<SchemeGraderService> list) {
        super(list);
    }
}
