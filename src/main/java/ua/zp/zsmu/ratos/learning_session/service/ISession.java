package ua.zp.zsmu.ratos.learning_session.service;

import ua.zp.zsmu.ratos.learning_session.model.Question;
import ua.zp.zsmu.ratos.learning_session.service.dto.QuestionDTO;
import ua.zp.zsmu.ratos.learning_session.service.dto.ResultDTO;
import ua.zp.zsmu.ratos.learning_session.service.dto.DetailedReportDTO;
import ua.zp.zsmu.ratos.learning_session.service.exceptions.TimeIsOverException;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Andrey on 4/8/2017.
 */
public interface ISession extends Serializable {

        Long getStoredSessionID();

        ResultDTO interruptSessionByStudent();

        ResultDTO interruptSessionByTimeout();

        ResultDTO finishSession();

        QuestionDTO provideNextQuestion() throws TimeIsOverException;

        void processStudentAnswer(List<Long> answers);

        Question provideAnswers();

        QuestionDTO skipQuestion();

        String provideHelp();

        String provideHint();

        QuestionResult getQuestionResult(Long qid) throws IllegalAccessException;

        DetailedReportDTO getSessionReport() throws IllegalAccessException;
}
