package ua.edu.ratos.service.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class CreateData {

    private SchemeDomain schemeDomain;

    private List<QuestionDomain> sequence;

    private int questionsPerBatch;

    private LocalDateTime sessionTimeOut;

    private long questionTimeOut;

}
