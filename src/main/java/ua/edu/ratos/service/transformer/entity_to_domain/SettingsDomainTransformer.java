package ua.edu.ratos.service.transformer.entity_to_domain;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Settings;
import ua.edu.ratos.service.domain.SettingsDomain;

@Slf4j
@Component
public class SettingsDomainTransformer {

    public SettingsDomain toDomain(@NonNull final Settings entity) {
        return new SettingsDomain()
                .setSetId(entity.getSetId())
                .setName(entity.getName())
                .setQuestionsPerSheet(entity.getQuestionsPerSheet())
                .setDaysKeepResultDetails(entity.getDaysKeepResultDetails())
                .setLevel2Coefficient(entity.getLevel2Coefficient())
                .setLevel3Coefficient(entity.getLevel3Coefficient())
                .setSecondsPerQuestion(entity.getSecondsPerQuestion())
                .setStrictControlTimePerQuestion(entity.isStrictControlTimePerQuestion());
    }

}
