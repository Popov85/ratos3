package ua.edu.ratos.service.dto.session.batch;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import ua.edu.ratos.service.dto.session.question.QuestionSessionOutDto;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@ToString(exclude = "batchMap")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BatchOutDto implements Serializable {

    private static final long serialVersionUID = 1L;

    // List is used to ensure the requested order of questions
    private final List<QuestionSessionOutDto> questions;

    // If this is the last batch,
    // this info is needed for non-dynamic sessions to perform finish call (when lastBatch = true)
    private final boolean lastBatch;

    @JsonIgnore// Only for look-up purposes
    private final Map<Long, QuestionSessionOutDto> batchMap;

    @Setter
    private Integer questionsLeft; // null if not displayed

    @Setter
    private Integer batchesLeft; // null if not displayed

    @Setter
    private Long sessionExpiresInSec; // null if not restricted

    @Setter
    private Long batchExpiresInSec; // null if not restricted

    @Setter
    private String currentScore; // null if prohibited to see

    @Setter
    private String effectiveScore; // null if prohibited to see

    @Setter
    private String progress; // null if prohibited to see

    @Setter//TODO
    private String motivationalMessage;

    // For creating empty questions (for dynamic sessions)
    // Empty questions is the hint for web-page to invoke finish request
    private BatchOutDto() {
        this.questions = Collections.EMPTY_LIST;
        this.batchMap = Collections.EMPTY_MAP;
        this.lastBatch = true;
    }

    // For creating a regular next questions
    private BatchOutDto(List<QuestionSessionOutDto> questions, boolean lastBatch) {
        if (questions.isEmpty()) throw new IllegalStateException("Failed to create BatchOutDto: wrong object state");
        this.questions = questions;
        this.batchMap = questions.stream().collect(Collectors.toMap(q -> q.getQuestionId(), q -> q));
        this.lastBatch = lastBatch;
    }

    // We create empty questions for dynamic types of sessions, when no more questions are left,
    // in order to notify the front-end script to launch finish request.
    public static BatchOutDto createEmpty() {
        return new BatchOutDto();
    }

    @JsonCreator
    public static BatchOutDto createRegular(@JsonProperty("questions") @NonNull final List<QuestionSessionOutDto> batch,
                                            @JsonProperty("lastBatch") boolean lastBatch) {
        return new BatchOutDto(batch, lastBatch);
    }

    @JsonIgnore
    public boolean isEmpty() {
        return this.questions ==null || this.questions.isEmpty();
    }
}
