package ua.edu.ratos.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.domain.entity.question.Question;
import ua.edu.ratos.domain.model.SessionData;
import ua.edu.ratos.domain.repository.SessionRepository;
import ua.edu.ratos.service.dto.response.ResponseMultipleChoice;
import ua.edu.ratos.service.dto.session.BatchIn;
import ua.edu.ratos.service.dto.session.BatchOut;
import ua.edu.ratos.domain.model.Result;

import java.util.*;


@Slf4j
@Service
public class GenericSessionServiceImpl implements GenericSessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public String start(@NonNull String user, @NonNull String scheme) {
        String key = UUID.randomUUID().toString();
        SessionData session = new SessionData(key, user, scheme, new ArrayList<>(40));
        SessionData savedSession = sessionRepository.save(session);
        log.info("Service: SessionData created: {}", savedSession);
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
        Optional<SessionData> session = sessionRepository.findById(key);
        SessionData s = session.get();
        int index = s.index;
        s.setIndex(++index);
        sessionRepository.save(s);
        BatchOut batchOut = new BatchOut();
        batchOut.setKey(s.getKey());
        batchOut.setTimeLeft(s.getTimeout());
        batchOut.setIndex(index);
        log.info("Service: SessionData proceeded: {}, next batchOut: {}", session, batchOut);
        return batchOut;
    }

    public SessionData status(@NonNull String key) {
        Optional<SessionData> session = sessionRepository.findById(key);
        SessionData s = session.get();
        Response response = new ResponseMultipleChoice();
        Question question = s.getQuestions().get(s.index);
        final int result = response.evaluateWith(new EvaluatorImpl(question));
        log.info("Service: SessionData startStatus: {}", s);
        return s;
    }

    @Deprecated
    public void removeSession(@NonNull String key) {
        sessionRepository.deleteById(key);
        log.info("Deleted key : {}", key);
    }


    @Override
    public Result cancel(String key) {
        return null;
    }

    public Optional<Question> findById(int qid, List<Question> questions) {
        return questions.stream().filter(q -> q.getQuestionId()==qid).findFirst();
    }

}
