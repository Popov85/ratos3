package ua.edu.ratos.service.dto.session.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.domain.SettingsFBDomain;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class AnswerFBSQSessionOutDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long answerId;

    private SettingsFBDomain settings;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerFBSQSessionOutDto that = (AnswerFBSQSessionOutDto) o;
        return Objects.equals(answerId, that.answerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerId);
    }
}
