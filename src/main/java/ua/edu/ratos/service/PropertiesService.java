package ua.edu.ratos.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component
public class PropertiesService {

    @Value("${ratos.data.collection.size}")
    private int initCollectionSize;

    private PageRequest collectionRequest;

    public PageRequest getInitCollectionSize() {
        return this.collectionRequest;
    }

    @Value("${ratos.data.table.size}")
    private int initTableSize;

    private PageRequest tableRequest;

    public PageRequest getInitTableSize() {
        return this.tableRequest;
    }

    @PostConstruct
    private void initialize() {
        this.collectionRequest = PageRequest.of(0, initCollectionSize);
        this.tableRequest = PageRequest.of(0, initTableSize);
    }

}
