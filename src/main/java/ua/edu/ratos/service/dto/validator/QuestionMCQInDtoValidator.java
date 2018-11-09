package ua.edu.ratos.service.dto.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.edu.ratos.service.dto.entity.AnswerMCQInDto;
import ua.edu.ratos.service.dto.entity.QuestionMCQInDto;
import java.util.Set;

@Component
public class QuestionMCQInDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return QuestionMCQInDto.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        QuestionMCQInDto dto = (QuestionMCQInDto) o;
        short controlSum = 0;
        Set<AnswerMCQInDto> answers = dto.getAnswers();
        for (AnswerMCQInDto answer : answers) {
            short percent = answer.getPercent();
            controlSum+=percent;
        }
        if (controlSum!=100) errors.reject("Control sum for MCQ is not 100, = "+controlSum);
    }
}
