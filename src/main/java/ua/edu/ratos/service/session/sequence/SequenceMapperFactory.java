package ua.edu.ratos.service.session.sequence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SequenceMapperFactory {

    private List<SequenceMapper> sequenceMappers;

    @Autowired
    public void setSequenceMappers(List<SequenceMapper> sequenceMappers) {
        this.sequenceMappers = sequenceMappers;
    }

    private Map<String, SequenceMapper> sequenceMapperMap = new HashMap<>();

    @PostConstruct
    public void init() {
        for(SequenceMapper sequenceMapper : sequenceMappers) {
            sequenceMapperMap.put(sequenceMapper.type(), sequenceMapper);
        }
    }

    public SequenceMapper getSequenceMapper(String type) {
        final SequenceMapper sequenceMapper = this.sequenceMapperMap.get(type);
        if(sequenceMapper == null) throw new RuntimeException("Unknown mapper type: " + type);
        return sequenceMapper;
    }
}
