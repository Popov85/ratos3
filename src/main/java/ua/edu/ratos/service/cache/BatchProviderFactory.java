package ua.edu.ratos.service.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.service.AbstractFactory;
import java.util.List;

@Slf4j
@Component
@SuppressWarnings("SpellCheckingInspection")
class BatchProviderFactory extends AbstractFactory<String, BatchProvider>{

    @Autowired
    BatchProviderFactory(List<BatchProvider> list) {
        super(list);
    }
}
