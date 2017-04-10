package ua.zp.zsmu.ratos.learning_session.service;

import ua.zp.zsmu.ratos.learning_session.model.*;

import java.io.Serializable;

/**
 * Created by Andrey on 4/8/2017.
 */
public interface ISession extends Serializable {

        Long getSID();

        void populatePersonalQuestionSequence(Scheme scheme);

        void startSession() throws IllegalStateException;

        /**
         * User can interrupt learning session at any time
         * This method should finish session quietly
         */
        void interruptSession();

        Question provideNextQuestion();

        void obtainStudentAnswer(Question question, Answer answer);

        void finishSession();

        Result calculateResult();
}
