package ua.edu.ratos.service.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class GradingDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long gradingId;

    private String name;
}
