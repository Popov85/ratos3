package ua.edu.ratos.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.domain.dao.SessionRepository;
import ua.edu.ratos.service.dto.Session;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SessionStoreService {

    @Autowired
    private SessionRepository sessionRepository;

    public List<Session> findAll() {
        Iterable<Session> all = sessionRepository.findAll();
        List<Session> target = new ArrayList<>();
        all.forEach(target::add);
        log.info("All active Sessions:: {}", target);
        return target;
    }

    public void removeAll() {
        sessionRepository.deleteAll();
        log.info("Deleted all active sessions!");
    }

}
