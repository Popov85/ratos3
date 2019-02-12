package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Department;
import ua.edu.ratos.service.dto.out.DepartmentOutDto;

@Component
public class DepartmentDtoTransformer {

    private FacultyDtoTransformer facultyDtoTransformer;

    @Autowired
    public void setFacultyDtoTransformer(FacultyDtoTransformer facultyDtoTransformer) {
        this.facultyDtoTransformer = facultyDtoTransformer;
    }

    public DepartmentOutDto toDto(@NonNull final Department entity) {
        return new DepartmentOutDto()
                .setDepId(entity.getDepId())
                .setName(entity.getName())
                .setFaculty(facultyDtoTransformer.toDto(entity.getFaculty()));
    }
}
