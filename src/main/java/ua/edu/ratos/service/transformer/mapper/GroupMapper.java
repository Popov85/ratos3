package ua.edu.ratos.service.transformer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.Group;
import ua.edu.ratos.service.dto.out.GroupOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {StudMapper.class, StaffMinMapper.class})
public interface GroupMapper {

    GroupOutDto toDto(Group entity);
}
