package ua.edu.ratos.service.domain.answer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.domain.ResourceDomain;
import ua.edu.ratos.service.dto.session.answer.AnswerMCQOutDto;
import java.util.Objects;
import java.util.Optional;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class AnswerMCQDomain {

    private Long answerId;

    private String answer;

    private short percent;

    private boolean isRequired;

    private ResourceDomain resourceDomain;

    // Nullable
    @JsonIgnore
    public Optional<ResourceDomain> getResourceDomain() {
        return Optional.ofNullable(resourceDomain);
    }

    @JsonProperty("resourceDomain")
    private ResourceDomain getResourceOrNothing() {
        return getResourceDomain().orElse(null);
    }

    public AnswerMCQOutDto toDto() {
        return new AnswerMCQOutDto()
                .setAnswerId(answerId)
                .setAnswer(answer)
                .setResourceDomain(resourceDomain);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerMCQDomain answerMCQDomain = (AnswerMCQDomain) o;
        return Objects.equals(answerId, answerMCQDomain.answerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerId);
    }
}
