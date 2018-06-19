package ua.edu.ratos.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.domain.entity.question.Question;
import ua.edu.ratos.domain.repository.SessionRepository;
import ua.edu.ratos.service.dto.*;
import java.util.*;


@Slf4j
@Service
public class GenericSessionImpl implements GenericSession {

    private final SessionRepository sessionRepository;

    @Autowired
    public GenericSessionImpl(SessionRepository sessionRepository) {
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

    @Override
    public BatchOut next(BatchIn batchIn) {
        return null;
    }

    @Override
    public Result finish(BatchIn batchIn) {
        return null;
    }

    public BatchOut proceed(@NonNull String key) {
        Optional<Session> session = sessionRepository.findById(key);
        Session s = session.get();
        int index = s.index;
        s.setIndex(++index);
        sessionRepository.save(s);
        BatchOut batchOut = new BatchOut();
        batchOut.setKey(s.getKey());
        batchOut.setTimeLeft(s.getTimeout());
        batchOut.setIndex(index);
        log.info("Service: Session proceeded: {}, next batchOut: {}", session, batchOut);
        return batchOut;
    }

    public Session status(@NonNull String key) {
        Optional<Session> session = sessionRepository.findById(key);
        Session s = session.get();
        Response response = new ResponseMultipleChoice();
        Question question = s.getQuestions().get(s.index);
        final int result = response.evaluateWith(new EvaluatorImpl(question));
        log.info("Service: Session startStatus: {}", s);
        return s;
    }

    @Deprecated
    public void removeSession(@NonNull String key) {
        sessionRepository.deleteById(key);
        log.info("Deleted key : {}", key);
    }


    @Override
    public void cancel(String key) {

    }

    public Optional<Question> findById(int qid, List<Question> questions) {
        return questions.stream().filter(q -> q.getQuestionId()==qid).findFirst();
    }

}
