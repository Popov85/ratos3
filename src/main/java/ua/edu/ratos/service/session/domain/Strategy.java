package ua.edu.ratos.service.session.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class Strategy {

    private Long strId;

    private String name;
}
