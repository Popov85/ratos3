package ua.edu.ratos.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component
public class PropertiesService {

    @Value("${ratos.data.collection.size}")
    private int initCollectionSize;

    private PageRequest pageRequest;

    public PageRequest getInitCollectionSize() {
        return this.pageRequest;
    }

    @PostConstruct
    private void initialize() {
        this.pageRequest = PageRequest.of(0, initCollectionSize);
    }

}
