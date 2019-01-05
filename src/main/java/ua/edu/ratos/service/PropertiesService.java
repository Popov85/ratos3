package ua.edu.ratos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import ua.edu.ratos.config.properties.AppProperties;
import javax.annotation.PostConstruct;

@Component
public class PropertiesService {

    @Autowired
    private AppProperties appProperties;

    private PageRequest collectionRequest;

    public PageRequest getInitCollectionSize() {
        return this.collectionRequest;
    }

    private PageRequest tableRequest;

    public PageRequest getInitTableSize() {
        return this.tableRequest;
    }

    @PostConstruct
    private void initialize() {
        this.collectionRequest = PageRequest.of(0, appProperties.getData().getCollection_size());
        this.tableRequest = PageRequest.of(0, appProperties.getData().getTable_size());
    }

}
