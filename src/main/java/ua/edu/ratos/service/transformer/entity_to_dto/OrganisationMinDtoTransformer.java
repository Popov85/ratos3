package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Organisation;
import ua.edu.ratos.service.dto.out.OrganisationMinOutDto;

@Deprecated
@Component
public class OrganisationMinDtoTransformer {

    public OrganisationMinOutDto toDto(@NonNull final Organisation entity) {
        return new OrganisationMinOutDto()
                .setOrgId(entity.getOrgId())
                .setName(entity.getName());
    }
}
