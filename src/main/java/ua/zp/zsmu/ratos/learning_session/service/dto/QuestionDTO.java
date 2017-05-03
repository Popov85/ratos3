package ua.zp.zsmu.ratos.learning_session.service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ua.zp.zsmu.ratos.learning_session.model.Resource;
import ua.zp.zsmu.ratos.learning_session.service.QuestionView;
import ua.zp.zsmu.ratos.learning_session.service.Student;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey on 4/23/2017.
 */
@Data
@Getter
public final class QuestionDTO implements Serializable {

        private static final long serialVersionUID = -6039208993180397074L;
        private final Long qid;
        private final String title;
        private final List<AnswerDTO> answers;
        private final SchemeDTO scheme;
        private final Student student;
        // scheme
        // student
        private final long timeLeft;
        private final int questionsLeft;
        private final double currentResult;

        @Setter
        private List<Resource> resources = new ArrayList<>();

        @Setter
        private QuestionView viewName = QuestionView.question;

        public QuestionDTO(Long qid, String title, List<AnswerDTO> answers, SchemeDTO scheme, Student student,
                           long timeLeft, int questionsLeft, double currentResult) {
                this.qid = qid;
                this.title = title;
                this.answers = answers;
                this.scheme = scheme;
                this.student = student;
                this.timeLeft = timeLeft;
                this.questionsLeft = questionsLeft;
                this.currentResult = currentResult;
        }
}
