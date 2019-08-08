package ua.edu.ratos.web.exception;

import lombok.extern.slf4j.Slf4j;
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
import java.util.Set;

@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse unknownException(Exception ex, WebRequest request) {
        final ExceptionResponse exceptionResponse =
                new ExceptionResponse(ex.getClass().toString(), ex.getMessage());
        log.error("Unknown error has occurred with message = {} for request = {}", ex.getMessage(), request.toString());
        return exceptionResponse;
    }

    @ExceptionHandler(value = SessionAlreadyOpenedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse openedException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse("SessionAlreadyOpenedException", "Previous session is still opened");
        log.error("Start request for already opened session for user = {} not finished schemeId = {}",
                request.getUserPrincipal().getName(), ((SessionAlreadyOpenedException) ex).getSchemeId());
        return exceptionResponse;
    }

    @ExceptionHandler(value = SessionNotFoundException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse notFoundException(Exception ex, WebRequest request) {
        Long schemeId = ((SessionNotFoundException) ex).getSchemeId();
        ExceptionResponse exceptionResponse =
                new ExceptionResponse("SessionNotFoundException", "The requested session is not found for schemeId ="+schemeId);
        log.error("No opened session is found for user = {} requesting schemeId = {}", request.getUserPrincipal().getName(), schemeId);
        return exceptionResponse;
    }

    @ExceptionHandler(value = RunOutOfTimeException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse timeException(Exception ex, WebRequest request) {
        final ExceptionResponse exceptionResponse =
                new ExceptionResponse("RunOutOfTimeException", "You have run out of time!");
        log.error("Session time limit is exceeded for user = {}", request.getUserPrincipal().getName());
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
                new ValidationExceptionResponse("Validation error", fieldValidationResponses);
        log.error("Validation error has occurred = {}", fieldValidationResponses);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
