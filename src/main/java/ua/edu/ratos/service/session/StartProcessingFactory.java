package ua.edu.ratos.service.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.service.AbstractFactory;

import java.util.List;

@Slf4j
@Component
public class StartProcessingFactory extends AbstractFactory<String, StartProcessingService> {

    @Autowired
    protected StartProcessingFactory(List<StartProcessingService> list) {
        super(list);
    }
}
