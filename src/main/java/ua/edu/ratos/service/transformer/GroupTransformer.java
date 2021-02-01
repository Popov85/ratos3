package ua.edu.ratos.service.transformer;

import ua.edu.ratos.dao.entity.Group;
import ua.edu.ratos.service.dto.in.GroupInDto;

public interface GroupTransformer {

    Group toEntity(GroupInDto dto);
}
