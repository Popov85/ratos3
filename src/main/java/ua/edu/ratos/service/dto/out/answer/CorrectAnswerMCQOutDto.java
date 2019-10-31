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
public class CorrectAnswerMCQOutDto {

    @Data
    public static class Triple {

        private Long answerId;

        private short percent;

        private boolean required;

        @JsonCreator
        public Triple(@JsonProperty("answerId") Long answerId,
                      @JsonProperty("percent") short percent,
                      @JsonProperty("required") boolean required) {
            this.answerId = answerId;
            this.percent = percent;
            this.required = required;
        }
    }

    private Set<Triple> correctAnswers;

}
