package ua.edu.ratos.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse unknownException(Exception ex, WebRequest request) {
        final ExceptionResponse exceptionResponse =
                new ExceptionResponse(getLocalizedMessage("advice.exception", request.getLocale()), ex.getMessage());
        log.error("Error has occurred :: {}", ex);
        return exceptionResponse;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        Set<FieldValidationResponse> fieldValidationResponses = new HashSet<>();
        fieldErrors.forEach(e ->
            fieldValidationResponses
                    .add(new FieldValidationResponse(e.getField(), e.getRejectedValue(), e.getDefaultMessage())));
        final ValidationExceptionResponse exceptionResponse =
                new ValidationExceptionResponse(getLocalizedMessage("advice.validation", request.getLocale()), fieldValidationResponses);
        log.error("Error has occurred :: {}", ex);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    private String getLocalizedMessage(String code, Locale locale) {
        return messageSource.getMessage(code, null, locale);
    }


}
