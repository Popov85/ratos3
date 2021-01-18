package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Department;
import ua.edu.ratos.service.dto.out.DepartmentOutDto;
import ua.edu.ratos.service.transformer.FacultyMapper;

@Deprecated
@Component
public class DepartmentDtoTransformer {

    private FacultyMapper facultyMapper;

    @Autowired
    public void setFacultyDtoTransformer(FacultyMapper facultyMapper) {
        this.facultyMapper = facultyMapper;
    }

    public DepartmentOutDto toDto(@NonNull final Department entity) {
        return new DepartmentOutDto()
                .setDepId(entity.getDepId())
                .setName(entity.getName())
                .setFaculty(facultyMapper.toDto(entity.getFaculty()));
    }
}
