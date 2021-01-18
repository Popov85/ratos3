package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Student;
import ua.edu.ratos.service.dto.out.StudMinOutDto;
import ua.edu.ratos.service.transformer.FacultyMinMapper;
import ua.edu.ratos.service.transformer.OrganisationMinMapper;
import ua.edu.ratos.service.transformer.UserMinMapper;

import java.util.Set;
import java.util.stream.Collectors;

@Deprecated
@Component
public class StudMinDtoTransformer {

    private UserMinMapper userMinMapper;

    private ClassMinDtoTransformer classMinDtoTransformer;

    private FacultyMinMapper facultyMinMapper;

    private OrganisationMinMapper organisationMinMapper;

    @Autowired
    public void setClassMinDtoTransformer(ClassMinDtoTransformer classMinDtoTransformer) {
        this.classMinDtoTransformer = classMinDtoTransformer;
    }

    @Autowired
    public void setUserMinDtoTransformer(UserMinMapper userMinMapper) {
        this.userMinMapper = userMinMapper;
    }

    @Autowired
    public void setFacultyMinDtoTransformer(FacultyMinMapper facultyMinMapper) {
        this.facultyMinMapper = facultyMinMapper;
    }

    @Autowired
    public void setOrganisationMinDtoTransformer(OrganisationMinMapper organisationMinMapper) {
        this.organisationMinMapper = organisationMinMapper;
    }

    public StudMinOutDto toDto(@NonNull final Student entity) {
        return new StudMinOutDto()
                .setStudId(entity.getStudId())
                .setUser(userMinMapper.toDto(entity.getUser()))
                .setStudentClass(classMinDtoTransformer.toDto(entity.getStudentClass()))
                .setFaculty(facultyMinMapper.toDto(entity.getFaculty()))
                .setOrganisation(organisationMinMapper.toDto(entity.getOrganisation()))
                .setEntranceYear(entity.getEntranceYear());
    }

    public Set<StudMinOutDto> toDto(@NonNull final Set<Student> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toSet());
    }
}
