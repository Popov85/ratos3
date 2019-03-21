package ua.edu.ratos.service.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Static data holder for info about a question
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class MetaData implements Serializable {

    private static final long serialVersionUID = 1L;

    private short help; // times a help for a question was requested by user
    private short skipped; // times a question was skipped
    private short incorrect; // times a question was answered incorrectly, 1 or more
}
