package ua.edu.ratos.service.session.domain.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ua.edu.ratos.service.session.Evaluator;

import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "className")
public class ResponseMatcher implements Response {

    private final Long questionId;
    private final Set<Triple> matchedPhrases;

    @JsonCreator
    public ResponseMatcher(@JsonProperty("questionId") Long questionId,
                           @JsonProperty("matchedPhrases") Set<Triple> matchedPhrases) {
        this.questionId = questionId;
        this.matchedPhrases = matchedPhrases;
    }

    @Getter
    @ToString
    public static class Triple {
        public final Long answerId;
        public final Long leftPhraseId;
        public final Long rightPhraseId;

        @JsonCreator
        public Triple(@JsonProperty("answerId") Long answerId,
                      @JsonProperty("leftPhraseId") Long leftPhraseId,
                      @JsonProperty("rightPhraseId") Long rightPhraseId) {
            this.answerId = answerId;
            this.leftPhraseId = leftPhraseId;
            this.rightPhraseId = rightPhraseId;
        }
    }

    @Override
    @JsonIgnore
    public boolean isNullable() {
        return (matchedPhrases==null || matchedPhrases.isEmpty());
    }

    @Override
    public Double evaluateWith(Evaluator evaluator) {
        return evaluator.evaluate(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseMatcher that = (ResponseMatcher) o;
        return questionId == that.questionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId);
    }
}
