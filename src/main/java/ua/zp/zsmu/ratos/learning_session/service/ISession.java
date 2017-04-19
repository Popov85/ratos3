package ua.zp.zsmu.ratos.learning_session.service;

import ua.zp.zsmu.ratos.learning_session.model.*;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Andrey on 4/8/2017.
 */
public interface ISession extends Serializable {

        Long getStoredSessionID();

        void startSession() throws IllegalStateException;

        void interruptSession();

        Question provideNextQuestion();

        void obtainStudentAnswer(Question question, List<Long> answers);

        Question skipQuestion();

        String provideHelp();

        String provideHint();

        SessionResult provideReport();
}
