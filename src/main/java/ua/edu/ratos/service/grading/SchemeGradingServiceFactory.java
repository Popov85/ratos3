package ua.edu.ratos.service.grading;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class SchemeGradingServiceFactory {

    private List<SchemeGraderService> schemeGraderServices;

    @Autowired
    public void setSchemeGraderServices(List<SchemeGraderService> schemeGraderServices) {
        this.schemeGraderServices = schemeGraderServices;
    }

    private final Map<Long, SchemeGraderService> graderServicesMap = new HashMap<>();

    @PostConstruct
    public void init() {
        for(SchemeGraderService service : schemeGraderServices) {
            graderServicesMap.put(service.type(), service);
        }
    }

    public SchemeGraderService getGraderService(long type) {
        SchemeGraderService schemeGraderService = graderServicesMap.get(type);
        if(schemeGraderService == null)
            throw new RuntimeException("Unknown schemeGraderService type = " + type);
        return schemeGraderService;
    }
}
