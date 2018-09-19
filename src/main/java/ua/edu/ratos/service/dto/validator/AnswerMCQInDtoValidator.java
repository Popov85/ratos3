package ua.edu.ratos.service.dto.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.edu.ratos.service.dto.entity.AnswerMCQInDto;

@Component
public class AnswerMCQInDtoValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return AnswerMCQInDto.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        AnswerMCQInDto dto = (AnswerMCQInDto) o;
        if (dto.getPercent()==0 && dto.isRequired()) {
            errors.reject("0/true","[dto.complex.incorrectRequired]");
        }
    }
}