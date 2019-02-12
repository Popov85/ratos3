package ua.edu.ratos.service.dto.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class StudOutDto {

    private Long studId;

    private UserOutDto user;

    private int entranceYear;

    private ClassOutDto studentClass;
}
