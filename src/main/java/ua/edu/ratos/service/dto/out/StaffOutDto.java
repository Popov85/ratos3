package ua.edu.ratos.service.dto.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class StaffOutDto  {

    private Long staffId;

    private UserOutDto user;

    private PositionOutDto position;

    private DepartmentOutDto department;

}
