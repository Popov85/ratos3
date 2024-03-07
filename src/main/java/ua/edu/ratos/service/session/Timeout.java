package ua.edu.ratos.service.session;

import ua.edu.ratos.service.domain.SessionData;

@SuppressWarnings("SpellCheckingInspection")
public interface Timeout {

    void controlSessionTimeout();

    boolean isTimeouted();

    boolean isSessionTimeout();

    boolean isBatchTimeout();

    void setTimeout(SessionData s);
}
