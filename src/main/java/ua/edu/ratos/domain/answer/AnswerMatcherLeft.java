package ua.edu.ratos.domain.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.edu.ratos.domain.model.Resource;


@Setter
@Getter
@ToString
public class AnswerMatcherLeft {
    private long leftPhraseId;
    private String leftPhrase;
    private Resource resource;
}
