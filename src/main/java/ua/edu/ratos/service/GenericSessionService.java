package ua.edu.ratos.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.domain.SessionRepository;
import ua.edu.ratos.service.dto.Batch;
import ua.edu.ratos.service.dto.Result;
import ua.edu.ratos.service.dto.Session;

import java.util.*;


@Slf4j
@Service
public class GenericSessionService implements GenericSession {

    private final SessionRepository sessionRepository;

    @Autowired
    public GenericSessionService(SessionRepository sessionRepository) {
        this.sessionRepository=sessionRepository;
    }

    @Override
    public String start(@NonNull String user, @NonNull String scheme) {
        String key = UUID.randomUUID().toString();
        Session session = new Session(key, user, scheme, new ArrayList<>(40));
        Session savedSession = sessionRepository.save(session);
        log.info("Service: Session created: {}", savedSession);
        return key;
    }

    public Batch proceed(@NonNull String key) {
        Optional<Session> session = sessionRepository.findById(key);
        Session s = session.get();
        int index = s.index;
        s.setIndex(++index);
        sessionRepository.save(s);
        Batch batch = new Batch();
        batch.setKey(s.getKey());
        batch.setTimeLeft(s.getTimeout());
        batch.setIndex(index);
        log.info("Service: Session proceeded: {}, next batch: {}", session, batch);
        return batch;
    }

    public Session status(@NonNull String key) {
        Optional<Session> session = sessionRepository.findById(key);
        Session s = session.get();
        log.info("Service: Session startStatus: {}", s);
        return s;
    }

    @Deprecated
    public void removeSession(@NonNull String key) {
        sessionRepository.deleteById(key);
        log.info("Deleted key : {}", key);
    }

    @Override
    public Batch next(Batch batch) {
        return null;
    }

    @Override
    public Result finish(Batch batch) {
        return null;
    }

    @Override
    public void cancel(String key) {

    }



}
