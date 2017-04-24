package ua.zp.zsmu.ratos.learning_session.service.dto;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
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
        private final List<AnswerDTO> questions;

        private final long timeLeft;
        private final int questionsLeft;
        private final double currentResult;

        public QuestionDTO(Long qid, String title, List<AnswerDTO> questions,
                           long timeLeft, int questionsLeft, double currentResult) {
                this.qid = qid;
                this.title = title;
                this.questions = questions;
                this.timeLeft = timeLeft;
                this.questionsLeft = questionsLeft;
                this.currentResult = currentResult;
        }
}
