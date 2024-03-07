package ua.edu.ratos.service.dto.out.answer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.util.Set;

@Getter
@ToString
@AllArgsConstructor
@Accessors(chain = true)
public class CorrectAnswerFBSQOutDto {

    private Set<String> acceptedPhrases;
}
