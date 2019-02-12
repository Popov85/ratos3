package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Student;
import ua.edu.ratos.service.dto.out.StudMinOutDto;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StudMinDtoTransformer {

    private UserMinDtoTransformer userMinDtoTransformer;

    private ClassDtoTransformer classDtoTransformer;

    @Autowired
    public void setClassDtoTransformer(ClassDtoTransformer classDtoTransformer) {
        this.classDtoTransformer = classDtoTransformer;
    }

    @Autowired
    public void setUserMinDtoTransformer(UserMinDtoTransformer userMinDtoTransformer) {
        this.userMinDtoTransformer = userMinDtoTransformer;
    }

    public StudMinOutDto toDto(@NonNull final Student entity) {
        return new StudMinOutDto()
                .setStudId(entity.getStudId())
                .setUser(userMinDtoTransformer.toDto(entity.getUser()))
                .setEntranceYear(entity.getEntranceYear())
                .setStudentClass(classDtoTransformer.toDto(entity.getStudentClass()));
    }

    public Set<StudMinOutDto> toDto(@NonNull final Set<Student> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toSet());
    }
}
