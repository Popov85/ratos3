package ua.edu.ratos.service.dto.out.answer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.Set;

@Getter
@ToString
@AllArgsConstructor
public class CorrectAnswerSQOutDto {

    @Data
    public static class Triple {

        private Long answerId;

        private Long phraseId;

        private short order;

        @JsonCreator
        public Triple(@JsonProperty("answerId") Long answerId,
                      @JsonProperty("phraseId") Long phraseId,
                      @JsonProperty("order") short order) {
            this.answerId = answerId;
            this.phraseId = phraseId;
            this.order = order;
        }
    }

    private Set<Triple> correctAnswers;
}
