package ua.edu.ratos.web.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@ToString
@Accessors(chain = true)
@AllArgsConstructor
public class FieldValidationResponse {
    private String field;
    private Object rejectedValue;
    private String defaultMessage;
}
