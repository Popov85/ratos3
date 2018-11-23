package ua.edu.ratos.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.session.domain.SessionData;
import ua.edu.ratos.dao.repository.SessionRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SessionStoreService {

    @Autowired
    private SessionRepository sessionRepository;

    public List<SessionData> findAll() {
        Iterable<SessionData> all = sessionRepository.findAll();
        List<SessionData> target = new ArrayList<>();
        all.forEach(target::add);
        log.info("All active Sessions:: {}", target);
        return target;
    }

    public void removeAll() {
        sessionRepository.deleteAll();
        log.info("Deleted all active sessions!");
    }

}
