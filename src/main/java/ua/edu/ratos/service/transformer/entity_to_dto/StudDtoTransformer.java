package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Student;
import ua.edu.ratos.service.dto.out.StudOutDto;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StudDtoTransformer {

    private UserDtoTransformer userDtoTransformer;

    private ClassDtoTransformer classDtoTransformer;

    @Autowired
    public void setClassDtoTransformer(ClassDtoTransformer classDtoTransformer) {
        this.classDtoTransformer = classDtoTransformer;
    }

    @Autowired
    public void setUserDtoTransformer(UserDtoTransformer userDtoTransformer) {
        this.userDtoTransformer = userDtoTransformer;
    }

    public StudOutDto toDto(@NonNull final Student entity) {
        return new StudOutDto()
                .setStudId(entity.getStudId())
                .setUser(userDtoTransformer.toDto(entity.getUser()))
                .setEntranceYear(entity.getEntranceYear())
                .setStudentClass(classDtoTransformer.toDto(entity.getStudentClass()));
    }

    public Set<StudOutDto> toDto(@NonNull final Set<Student> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toSet());
    }
}
