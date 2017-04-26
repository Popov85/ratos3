package ua.zp.zsmu.ratos.learning_session.service.exceptions;

/**
 * Created by Andrey on 24.04.2017.
 */
public class LostSessionException extends RuntimeException {
        public LostSessionException(String message) {
                super(message);
        }
}
