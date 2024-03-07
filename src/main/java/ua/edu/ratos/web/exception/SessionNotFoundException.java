package ua.edu.ratos.web.exception;

import lombok.Getter;

@Getter
public class SessionNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "The opened session for the requested schemeId is not found";

    private final Long schemeId;

    public SessionNotFoundException(Long schemeId) {
        super(DEFAULT_MESSAGE);
        this.schemeId = schemeId;
    }
}
