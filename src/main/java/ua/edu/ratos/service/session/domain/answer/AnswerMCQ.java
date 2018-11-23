package ua.edu.ratos.service.session.domain.answer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.session.domain.Resource;
import ua.edu.ratos.service.session.dto.answer.AnswerMCQOutDto;
import java.util.Objects;
import java.util.Optional;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class AnswerMCQ {

    private Long answerId;

    private String answer;

    private short percent;

    private boolean isRequired;

    private Resource resource;

    // Nullable
    @JsonIgnore
    public Optional<Resource> getResource() {
        return Optional.ofNullable(resource);
    }

    @JsonProperty("resource")
    private Resource getResourceOrNothing() {
        return getResource().orElse(null);
    }

    public AnswerMCQOutDto toDto() {
        return new AnswerMCQOutDto()
                .setAnswerId(answerId)
                .setAnswer(answer)
                .setResource(resource);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerMCQ answerMCQ = (AnswerMCQ) o;
        return Objects.equals(answerId, answerMCQ.answerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerId);
    }
}
