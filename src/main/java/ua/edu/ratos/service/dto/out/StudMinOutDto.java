package ua.edu.ratos.service.dto.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class StudMinOutDto {

    private Long studId;

    private UserMinOutDto user;

    private int entranceYear;

    private ClassOutDto studentClass;
}
