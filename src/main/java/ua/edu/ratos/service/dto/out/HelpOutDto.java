package ua.edu.ratos.service.dto.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class HelpOutDto {
    private Long helpId;

    private String name;

    private String help;

    private ResourceOutDto resource;
}
