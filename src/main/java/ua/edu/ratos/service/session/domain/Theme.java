package ua.edu.ratos.service.session.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Theme {

    private Long themeId;

    private String name;
}
