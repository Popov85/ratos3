package ua.edu.ratos.service.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class StrategyDomain {

    private Long strId;

    private String name;
}
