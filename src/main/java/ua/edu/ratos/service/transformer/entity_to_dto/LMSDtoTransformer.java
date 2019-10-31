package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.lms.LMS;
import ua.edu.ratos.service.dto.out.LMSOutDto;

@Component
public class LMSDtoTransformer {

    private LTICredentialsDtoTransformer ltiCredentialsDtoTransformer;

    private LTIVersionDtoTransformer ltiVersionDtoTransformer;


    @Autowired
    public void setLtiCredentialsDtoTransformer(LTICredentialsDtoTransformer ltiCredentialsDtoTransformer) {
        this.ltiCredentialsDtoTransformer = ltiCredentialsDtoTransformer;
    }

    @Autowired
    public void setLtiVersionDtoTransformer(LTIVersionDtoTransformer ltiVersionDtoTransformer) {
        this.ltiVersionDtoTransformer = ltiVersionDtoTransformer;
    }


    public LMSOutDto toDto(@NonNull final LMS entity) {
        return new LMSOutDto()
                .setLmsId(entity.getLmsId())
                .setName(entity.getName())
                .setCredentials(ltiCredentialsDtoTransformer.toDto(entity.getCredentials()))
                .setLtiVersion(ltiVersionDtoTransformer.toDto(entity.getLtiVersion()));
    }
}
