package ua.edu.ratos.service.dto.out.answer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;
import java.util.Set;

@Getter
@ToString
@AllArgsConstructor
@Accessors(chain = true)
public class CorrectAnswerFBMQOutDto {

    @Data
    public static class Pair {

        private Long answerId;

        private Set<String> acceptedPhrases;

        @JsonCreator
        public Pair(@JsonProperty("answerId") Long answerId,
                      @JsonProperty("acceptedPhrases") Set<String> acceptedPhrases) {
            this.answerId = answerId;
            this.acceptedPhrases = acceptedPhrases;
        }
    }

    private Set<Pair> correctAnswers;
}
