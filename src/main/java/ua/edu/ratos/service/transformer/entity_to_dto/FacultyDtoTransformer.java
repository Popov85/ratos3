package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Faculty;
import ua.edu.ratos.service.dto.out.FacultyOutDto;
import ua.edu.ratos.service.transformer.OrganisationMapper;

@Deprecated
@Component
public class FacultyDtoTransformer {

    private OrganisationMapper organisationMapper;

    @Autowired
    public void setOrganisationDtoTransformer(OrganisationMapper organisationMapper) {
        this.organisationMapper = organisationMapper;
    }

    public FacultyOutDto toDto(@NonNull final Faculty entity) {
        return new FacultyOutDto()
                .setFacId(entity.getFacId())
                .setName(entity.getName())
                .setOrganisation(organisationMapper.toDto(entity.getOrganisation()));
    }
}
