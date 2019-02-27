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

    private ClassMinDtoTransformer classMinDtoTransformer;

    private FacultyMinDtoTransformer facultyMinDtoTransformer;

    private OrganisationMinDtoTransformer organisationMinDtoTransformer;

    @Autowired
    public void setClassMinDtoTransformer(ClassMinDtoTransformer classMinDtoTransformer) {
        this.classMinDtoTransformer = classMinDtoTransformer;
    }

    @Autowired
    public void setUserDtoTransformer(UserDtoTransformer userDtoTransformer) {
        this.userDtoTransformer = userDtoTransformer;
    }

    @Autowired
    public void setFacultyMinDtoTransformer(FacultyMinDtoTransformer facultyMinDtoTransformer) {
        this.facultyMinDtoTransformer = facultyMinDtoTransformer;
    }

    @Autowired
    public void setOrganisationMinDtoTransformer(OrganisationMinDtoTransformer organisationMinDtoTransformer) {
        this.organisationMinDtoTransformer = organisationMinDtoTransformer;
    }

    public StudOutDto toDto(@NonNull final Student entity) {
        return new StudOutDto()
                .setStudId(entity.getStudId())
                .setUser(userDtoTransformer.toDto(entity.getUser()))
                .setStudentClass(classMinDtoTransformer.toDto(entity.getStudentClass()))
                .setFaculty(facultyMinDtoTransformer.toDto(entity.getFaculty()))
                .setOrganisation(organisationMinDtoTransformer.toDto(entity.getOrganisation()))
                .setEntranceYear(entity.getEntranceYear());
    }

    public Set<StudOutDto> toDto(@NonNull final Set<Student> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toSet());
    }
}
