package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Clazz;
import ua.edu.ratos.service.dto.out.ClassOutDto;
import ua.edu.ratos.service.transformer.FacultyMapper;

@Deprecated
@Component
public class ClassDtoTransformer {

    private FacultyMapper facultyMapper;

    @Autowired
    public void setFacultyDtoTransformer(FacultyMapper facultyMapper) {
        this.facultyMapper = facultyMapper;
    }

    public ClassOutDto toDto(@NonNull final Clazz entity) {
        return new ClassOutDto()
                .setClassId(entity.getClassId())
                .setName(entity.getName())
                .setFaculty(facultyMapper.toDto(entity.getFaculty()));
    }
}
