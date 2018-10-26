package ua.edu.ratos.service.session.sequence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.domain.entity.Strategy;
import ua.edu.ratos.service.session.grade.Grader;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SequenceFactory {

    @Autowired
    private List<SequenceProducer> sequenceProducers;

    private Map<String, SequenceProducer> sequenceProducerMap = new HashMap<>();

    @PostConstruct
    public void init() {
        for(SequenceProducer sequenceProducer : sequenceProducers) {
            sequenceProducerMap.put(sequenceProducer.type(), sequenceProducer);
        }
    }

    public SequenceProducer getSequenceProducer(Strategy strategy) {
        final String name = strategy.getName();
        final SequenceProducer sequenceProducer = this.sequenceProducerMap.get(name);
        if(sequenceProducer == null) throw new RuntimeException("Unknown strategy type: " + name);
        return sequenceProducer;

    }
}
