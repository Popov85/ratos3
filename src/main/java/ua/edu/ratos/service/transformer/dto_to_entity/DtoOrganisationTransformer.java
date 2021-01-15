package ua.edu.ratos.service.transformer.dto_to_entity;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Organisation;
import ua.edu.ratos.service.dto.in.OrganisationInDto;

@Deprecated
@Component
@AllArgsConstructor
public class DtoOrganisationTransformer {

    private final ModelMapper modelMapper;

    @Transactional(propagation = Propagation.MANDATORY)
    public Organisation toEntity(@NonNull final OrganisationInDto dto) {
        Organisation org = modelMapper.map(dto, Organisation.class);
        return org;
    }
}
