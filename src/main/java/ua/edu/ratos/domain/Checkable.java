package ua.edu.ratos.domain;

import ua.edu.ratos.service.dto.Response;


/**
 * Interface that every Question type should implement
 */
public interface Checkable<T extends Response> {
    /**
     * Checks if the answer provided by user is correct
     * @param response
     * @return
     */
    int check(T response);
}
