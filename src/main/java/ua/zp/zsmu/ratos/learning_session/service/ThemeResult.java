package ua.zp.zsmu.ratos.learning_session.service;

import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.Getter;
import ua.zp.zsmu.ratos.learning_session.model.Theme;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Andrey on 19.04.2017.
 */
@Data
@Getter
public final class ThemeResult implements Serializable {

        private static final long serialVersionUID = -4516674989770605753L;

        private double result;
        private final Theme theme;
        private int questionsQuantity;
        private final List<QuestionResult> questionResults;

        public ThemeResult(@NotNull final Theme theme, final int questionsQuantity,
                           @NotNull final List<QuestionResult> questionResults) {
                if (questionsQuantity==0) throw new IllegalArgumentException();
                this.theme = theme;
                this.questionsQuantity = questionsQuantity;
                this.questionResults = questionResults;
        }

        /**
         * Calculates the result in % on this theme
         * @return %-based result on this Theme
         */
        public double calculateResult(){
                double result=0;
                for (QuestionResult questionResult : questionResults) {
                        result+=questionResult.getResult()/100;
                }
                result = result*100/questionsQuantity;
                this.result = result;
                return result;
        }
}
