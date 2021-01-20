package ua.edu.ratos.service.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.Phrase;
import ua.edu.ratos.service.domain.PhraseDomain;
import ua.edu.ratos.service.dto.out.PhraseOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {StaffMinMapper.class, ResourceMapper.class})
public interface PhraseMapper {

    PhraseOutDto toDto(Phrase entity);

    @Mapping(target = "resourceDomain", source = "resource")
    PhraseDomain toDomain(Phrase entity);
}
