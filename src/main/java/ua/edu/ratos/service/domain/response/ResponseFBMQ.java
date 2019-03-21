package ua.edu.ratos.service.domain.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.ToString;
import ua.edu.ratos.service.session.Evaluator;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "className")
public class ResponseFBMQ implements Response, Serializable {

    private static final long serialVersionUID = 1L;

    private final Long questionId;
    private final Set<Pair> enteredPhrases;

    @JsonCreator
    public ResponseFBMQ(@JsonProperty("questionId") Long questionId,
                        @JsonProperty("enteredPhrases") Set<Pair> enteredPhrases) {
        this.questionId = questionId;
        this.enteredPhrases = enteredPhrases;
    }

    @Getter
    @ToString
    public static class Pair implements Serializable {

        private static final long serialVersionUID = 1L;

        private final Long answerId;
        private final String enteredPhrase;

        @JsonCreator
        public Pair(@JsonProperty("answerId") Long answerId, @JsonProperty("enteredPhrase") String enteredPhrase) {
            this.answerId = answerId;
            this.enteredPhrase = enteredPhrase;
        }
    }

    @Override
    @JsonIgnore
    public boolean isNullable() {
        return (enteredPhrases==null || enteredPhrases.isEmpty());
    }

    @Override
    public Double evaluateWith(Evaluator evaluator) {
        return evaluator.evaluate(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseFBMQ that = (ResponseFBMQ) o;
        return questionId == that.questionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId);
    }
}
