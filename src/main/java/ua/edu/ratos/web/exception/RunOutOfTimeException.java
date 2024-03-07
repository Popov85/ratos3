package ua.edu.ratos.web.exception;

import lombok.Getter;

@Getter
public class RunOutOfTimeException extends RuntimeException {

    private static final String DEFAULT_TIMEOUT_MESSAGE = "You've run out of time";

    public RunOutOfTimeException() {
        super(DEFAULT_TIMEOUT_MESSAGE);
    }
}
