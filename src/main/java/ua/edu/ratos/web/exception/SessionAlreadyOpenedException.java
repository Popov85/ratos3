package ua.edu.ratos.web.exception;

import lombok.Getter;

@Getter
public class SessionAlreadyOpenedException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "You have not finished the previous session";

    private final Long schemeId;

    public SessionAlreadyOpenedException(Long schemeId) {
        super(DEFAULT_MESSAGE);
        this.schemeId = schemeId;
    }
}
