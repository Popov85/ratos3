package ua.zp.zsmu.ratos.learning_session.service.exceptions;

/**
 * Throw when a student tries to repeatedly aNSWER A QUESTION
 * after an answer has already been submitted
 * Created by Andrey on 27.04.2017.
 */
public class QuestionAlreadyAnsweredException extends Exception {
        public QuestionAlreadyAnsweredException(String message) {
                super(message);
        }

}
