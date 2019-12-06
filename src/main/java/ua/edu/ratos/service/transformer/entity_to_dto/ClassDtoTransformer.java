package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Clazz;
import ua.edu.ratos.service.dto.out.ClassOutDto;

@Component
public class ClassDtoTransformer {

    private FacultyDtoTransformer facultyDtoTransformer;

    @Autowired
    public void setFacultyDtoTransformer(FacultyDtoTransformer facultyDtoTransformer) {
        this.facultyDtoTransformer = facultyDtoTransformer;
    }

    public ClassOutDto toDto(@NonNull final Clazz entity) {
        return new ClassOutDto()
                .setClassId(entity.getClassId())
                .setName(entity.getName())
                .setFaculty(facultyDtoTransformer.toDto(entity.getFaculty()));
    }
}
