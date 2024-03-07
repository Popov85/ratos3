package ua.edu.ratos.web.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Set;

@Setter
@Getter
@ToString
@Accessors(chain = true)
@AllArgsConstructor
public class ValidationExceptionResponse {
    private String message;
    private Set<FieldValidationResponse> fieldValidationResponses;
}
