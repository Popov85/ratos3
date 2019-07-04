package ua.edu.ratos.web.exception;

import lombok.Getter;

@Getter
public class SessionOpenedExceptionResponse extends ExceptionResponse {

    private static final String TYPE = "SessionAlreadyOpenedException";

    private static final String MESSAGE = "Previous session is still opened";

    private final Long schemeId;

    public SessionOpenedExceptionResponse(Long schemeId) {
        super(TYPE, MESSAGE);
        this.schemeId = schemeId;
    }
}
