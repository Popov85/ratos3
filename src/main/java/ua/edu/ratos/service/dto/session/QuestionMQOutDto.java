package ua.edu.ratos.service.dto.session;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class QuestionMQOutDto extends QuestionOutDto {

    /**
     * Set of leftPhrases with possible resources
     */
    private Set<AnswerMQOutDto> leftPhrases = new HashSet<>();

    /**
     * Randomized set of rightPhrases
     */
    private Set<String> rightPhrases = new HashSet<>();

    public void addLeftPhrase(AnswerMQOutDto leftPhrase) {
        this.leftPhrases.add(leftPhrase);
    }

    public void addRightPhrase(String rightPhrase) {
        this.rightPhrases.add(rightPhrase);
    }

}
