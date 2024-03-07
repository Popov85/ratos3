package ua.edu.ratos.service.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.edu.ratos.service.dto.in.report.ReportOnContentInDto;

@Component
public class ReportOnContentInDtoValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return ReportOnContentInDto.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ReportOnContentInDto dto = (ReportOnContentInDto) o;
        if (!dto.isCourses() && !dto.isLmsCourses() && !dto.isSchemes() && !dto.isThemes() && !dto.isQuestions()) {
            errors.reject("dto.report.noMaterials","No materials is selected for the report!");
        }
    }
}