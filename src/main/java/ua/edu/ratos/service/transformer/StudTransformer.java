package ua.edu.ratos.service.transformer;

import ua.edu.ratos.dao.entity.Student;
import ua.edu.ratos.service.dto.in.StudentInDto;

public interface StudTransformer {

    Student toEntity(StudentInDto dto);
}
