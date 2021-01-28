package ua.edu.ratos.service.transformer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.Organisation;
import ua.edu.ratos.service.dto.in.OrganisationInDto;
import ua.edu.ratos.service.dto.out.OrganisationOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {ReferenceMapper.class})
public interface OrganisationMapper {

    OrganisationOutDto toDto(Organisation entity);

    Organisation toEntity(OrganisationInDto dto);

    Organisation toEntity(Long id);
}
