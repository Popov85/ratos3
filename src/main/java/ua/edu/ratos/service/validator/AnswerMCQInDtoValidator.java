package ua.edu.ratos.service.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.edu.ratos.service.dto.in.AnswerMCQInDto;

/**
 * 0 percent options cannot be required! See custom validator for details
 */
@Slf4j
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
            errors.reject("dto.complex.incorrectRequired","Incorrect answers cannot be required");
        }
    }
}