package ua.edu.ratos.service.session.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Static data holder for info about a question
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class MetaData {
    private short help; // times a helpAvailable for a question was requested by user
    private short skipped; // times a question was skipped
    private short incorrect; // times a question was answered incorrectly, 1 or more
    private Byte starred; // is this question was starred by user? null if not starred
    // TODO complained with some text
    private boolean complained; // did user complain about this question?
}
