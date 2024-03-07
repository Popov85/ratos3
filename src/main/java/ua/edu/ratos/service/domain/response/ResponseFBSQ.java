package ua.edu.ratos.service.domain.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

@Getter
@ToString
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "className")
public class ResponseFBSQ implements Response, Serializable {

    private static final long serialVersionUID = 1L;

    private final Long questionId;
    private final String enteredPhrase;

    @JsonCreator
    public ResponseFBSQ(@JsonProperty("questionId") Long questionId,
                        @JsonProperty("enteredPhrase") String enteredPhrase) {
        this.questionId = questionId;
        this.enteredPhrase = enteredPhrase;
    }

    @Override
    @JsonIgnore
    public boolean isNullable() {
        return (enteredPhrase==null || enteredPhrase.isEmpty());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseFBSQ that = (ResponseFBSQ) o;
        return questionId == that.questionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId);
    }
}
