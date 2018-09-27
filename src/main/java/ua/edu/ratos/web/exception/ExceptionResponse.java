package ua.edu.ratos.web.exception;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class ExceptionResponse {
    private Date timestamp;
    private String message;
    private String details;

    public ExceptionResponse(String message, String details) {
        this.timestamp = new Date();
        this.message = message;
        this.details = details;
    }
}
