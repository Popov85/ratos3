package ua.edu.ratos.service.dto.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class FacultyOutDto {

    private Long facId;

    private String name;

    private OrganisationOutDto organisation;
}
