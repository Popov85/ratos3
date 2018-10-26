package ua.edu.ratos.web.exception;

import lombok.Getter;

@Getter
public class RunOutOfTimeException extends RuntimeException {

    private static final String DEFAULT_TIMEOUT_MESSAGE = "You've run out of time";

    private final boolean sessionTimeOut;

    private final boolean batchTimeOut;

    public RunOutOfTimeException(boolean batchTimeOut, boolean sessionTimeOut) {
        super(DEFAULT_TIMEOUT_MESSAGE);
        this.sessionTimeOut = sessionTimeOut;
        this.batchTimeOut = batchTimeOut;
    }

    public RunOutOfTimeException(String errorMessage, boolean batchTimeOut, boolean sessionTimeOut) {
        super(errorMessage);
        this.sessionTimeOut = sessionTimeOut;
        this.batchTimeOut = batchTimeOut;
    }
}
