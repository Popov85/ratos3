package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Organisation;
import ua.edu.ratos.service.dto.out.OrganisationOutDto;

@Deprecated
@Component
public class OrganisationDtoTransformer {

    public OrganisationOutDto toDto(@NonNull final Organisation entity) {
        return new OrganisationOutDto()
                .setOrgId(entity.getOrgId())
                .setName(entity.getName())
                .setDeleted(entity.isDeleted());
    }
}
