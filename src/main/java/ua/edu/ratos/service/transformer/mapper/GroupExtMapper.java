package ua.edu.ratos.service.transformer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.Group;
import ua.edu.ratos.service.dto.out.GroupExtendedOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {StaffMinMapper.class})
public interface GroupExtMapper {

    @Mapping(target = "students", expression = "java(entity.getStudents() !=null ? entity.getStudents().size() : null)")
    GroupExtendedOutDto toDto(Group entity);
}
