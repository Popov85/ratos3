package ua.edu.ratos.service.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.edu.ratos.service.dto.in.AnswerMCQInDto;
import ua.edu.ratos.service.dto.in.QuestionMCQInDto;
import java.util.Set;

@Slf4j
@Component
public class QuestionMCQInDtoValidator implements Validator {

    @Autowired
    private AnswerMCQInDtoValidator answerMCQInDtoValidator;

    @Override
    public boolean supports(Class<?> aClass) {
        return QuestionMCQInDto.class.equals(aClass);
        //return QuestionMCQInDto.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        QuestionMCQInDto dto = (QuestionMCQInDto) o;
        short controlSum = 0;
        // All answer strings should be unique out-of-the-box, see equals and hashcode impl.
        Set<AnswerMCQInDto> answers = dto.getAnswers();
        for (AnswerMCQInDto answer : answers) {
            short percent = answer.getPercent();
            controlSum+=percent;
            answerMCQInDtoValidator.validate(answer, errors);
        }
        if (controlSum!=100) errors.reject("dto.complex.controlSumGreater100", "Control sum for MCQ is not 100, = "+controlSum);
    }
}
