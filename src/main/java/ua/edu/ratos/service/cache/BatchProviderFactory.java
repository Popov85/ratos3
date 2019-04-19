package ua.edu.ratos.service.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Component
@SuppressWarnings("SpellCheckingInspection")
class BatchProviderFactory {

    private final List<BatchProvider> batchProviders;

    @Autowired
    BatchProviderFactory(List<BatchProvider> batchProviders) {
        this.batchProviders = batchProviders;
    }

    private final Map<String, BatchProvider> batchProviderMap = new HashMap<>();

    @PostConstruct
    void init() {
        for(BatchProvider batchProvider : batchProviders) {
            batchProviderMap.put(batchProvider.type(), batchProvider);
        }
    }

    BatchProvider getBatchProvider(String type) {
        BatchProvider batchProvider = batchProviderMap.get(type);
        if(batchProvider == null)
            throw new RuntimeException("Unknown batch provider type = " + type);
        return batchProvider;
    }
}
