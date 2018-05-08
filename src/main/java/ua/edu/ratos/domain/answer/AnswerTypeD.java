package ua.edu.ratos.domain.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.edu.ratos.domain.Answer;
import ua.edu.ratos.domain.Resource;
import java.util.Optional;

/**
 * Match.
 * Matching questions consist of two columns, typically one column is on the left and one column is on the right.
 * We will refer to the left side as 'Clues' and the right side as 'Matches'.
 * The objective is to pair the clues on the left side with their matches on the right.
 * These can be created with using text on both sides or a mix of text with media,
 * such as images, audio or video.
 *
 * @see <a href="https://www.classmarker.com/learn/question-types/matching-questions/">Match</a>
 * @author Andrey P.
 */

@Setter
@Getter
@ToString
public class AnswerTypeD implements Answer {
    private long answerId;
    private String leftPhrase;
    private String rightPhrase;
    private short percent;
    private boolean isRequired;
    private Optional<Resource> resource;

}
