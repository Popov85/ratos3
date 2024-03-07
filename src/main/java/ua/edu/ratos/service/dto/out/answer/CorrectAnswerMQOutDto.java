package ua.edu.ratos.service.dto.out.answer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.Set;

@Getter
@ToString
@AllArgsConstructor
public class CorrectAnswerMQOutDto {
    @Data
    public static class Triple {

        private Long answerId;

        private Long leftPhraseId;

        private Long rightPhraseId;

        @JsonCreator
        public Triple(@JsonProperty("answerId") Long answerId,
                      @JsonProperty("leftPhraseId") Long leftPhraseId,
                      @JsonProperty("rightPhraseId") Long rightPhraseId) {
            this.answerId = answerId;
            this.leftPhraseId = leftPhraseId;
            this.rightPhraseId = rightPhraseId;
        }
    }

    private Set<Triple> correctAnswers;
}

