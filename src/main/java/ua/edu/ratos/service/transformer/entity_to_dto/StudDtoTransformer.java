package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Student;
import ua.edu.ratos.service.dto.out.StudOutDto;
import ua.edu.ratos.service.transformer.ClassMinMapper;
import ua.edu.ratos.service.transformer.FacultyMinMapper;
import ua.edu.ratos.service.transformer.OrganisationMinMapper;
import ua.edu.ratos.service.transformer.UserMapper;

import java.util.Set;
import java.util.stream.Collectors;

@Deprecated
@Component
public class StudDtoTransformer {

    private UserMapper userMapper;

    private ClassMinMapper classMinMapper;

    private FacultyMinMapper facultyMinMapper;

    private OrganisationMinMapper organisationMinMapper;

    @Autowired
    public void setClassMinDtoTransformer(ClassMinMapper classMinMapper) {
        this.classMinMapper = classMinMapper;
    }

    @Autowired
    public void setUserDtoTransformer(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setFacultyMinDtoTransformer(FacultyMinMapper facultyMinMapper) {
        this.facultyMinMapper = facultyMinMapper;
    }

    @Autowired
    public void setOrganisationMinDtoTransformer(OrganisationMinMapper organisationMinMapper) {
        this.organisationMinMapper = organisationMinMapper;
    }

    public StudOutDto toDto(@NonNull final Student entity) {
        return new StudOutDto()
                .setStudId(entity.getStudId())
                .setUser(userMapper.toDto(entity.getUser()))
                .setStudentClass(classMinMapper.toDto(entity.getStudentClass()))
                .setFaculty(facultyMinMapper.toDto(entity.getFaculty()))
                .setOrganisation(organisationMinMapper.toDto(entity.getOrganisation()))
                .setEntranceYear(entity.getEntranceYear());
    }

    public Set<StudOutDto> toDto(@NonNull final Set<Student> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toSet());
    }
}
