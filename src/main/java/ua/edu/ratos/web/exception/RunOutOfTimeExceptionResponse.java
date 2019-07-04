package ua.edu.ratos.web.exception;

import lombok.Getter;

@Getter
public class RunOutOfTimeExceptionResponse extends ExceptionResponse {

    private static final String TYPE = "RunOutOfTimeExceptionResponse";

    private static final String MESSAGE = "You have run out of time...";

    public RunOutOfTimeExceptionResponse() {
        super(TYPE, MESSAGE);
    }
}
