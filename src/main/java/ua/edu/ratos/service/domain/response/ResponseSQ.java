package ua.edu.ratos.service.domain.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
@ToString
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "className")
public class ResponseSQ implements Response, Serializable {

    private static final long serialVersionUID = 1L;

    private final Long questionId;
    private final List<Long> answerIds;

    @JsonCreator
    public ResponseSQ(@JsonProperty("questionId") Long questionId,
                      @JsonProperty("answersIds") List<Long> answerIds) {
        this.questionId = questionId;
        this.answerIds = answerIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseSQ that = (ResponseSQ) o;
        return questionId == that.questionId;
    }

    @Override
    @JsonIgnore
    public boolean isNullable() {
        return (answerIds==null || answerIds.isEmpty());
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId);
    }
}
