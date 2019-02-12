package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Faculty;
import ua.edu.ratos.service.dto.out.FacultyOutDto;

@Component
public class FacultyDtoTransformer {

    private OrganisationDtoTransformer organisationDtoTransformer;

    @Autowired
    public void setOrganisationDtoTransformer(OrganisationDtoTransformer organisationDtoTransformer) {
        this.organisationDtoTransformer = organisationDtoTransformer;
    }

    public FacultyOutDto toDto(@NonNull final Faculty entity) {
        return new FacultyOutDto()
                .setFacId(entity.getFacId())
                .setName(entity.getName())
                .setOrganisation(organisationDtoTransformer.toDto(entity.getOrganisation()));
    }
}
