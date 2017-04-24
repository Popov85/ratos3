package ua.zp.zsmu.ratos.learning_session.service;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

/**
 * Created by Andrey on 19.04.2017.
 */
@Data
@Getter
public final class QuestionStatistics implements Serializable {

        private static final long serialVersionUID = 4033815058841410998L;
        /**
         * Time taken to answer this question (ms)
         */
        private long timeTaken;
        private boolean isHintTaken;
        private boolean isHelpTaken;
        /**
         * Number of occasions the question has been skipped by a student
         */
        private int skipped = 0;

        public QuestionStatistics() {}

        public QuestionStatistics(long timeTaken) {
                this.timeTaken = timeTaken;
        }

        public QuestionStatistics(long timeTaken, boolean isHintTaken, boolean isHelpTaken) {
                this.timeTaken = timeTaken;
                this.isHintTaken = isHintTaken;
                this.isHelpTaken = isHelpTaken;
        }

        public void increaseTimeTaken(long addTimeTaken) {
                this.timeTaken+=addTimeTaken;
        }

        public void skipIt() {
                this.skipped++;
        }
}
