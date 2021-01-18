package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.SettingsFB;
import ua.edu.ratos.service.dto.out.SettingsFBOutDto;
import ua.edu.ratos.service.transformer.StaffMinMapper;

@Component
public class SettingsFBDtoTransformer {

    private LanguageDtoTransformer languageDtoTransformer;

    private StaffMinMapper staffMinMapper;

    @Autowired
    public void setLanguageDtoTransformer(LanguageDtoTransformer languageDtoTransformer) {
        this.languageDtoTransformer = languageDtoTransformer;
    }

    @Autowired
    public void setStaffMinDtoTransformer(StaffMinMapper staffMinMapper) {
        this.staffMinMapper = staffMinMapper;
    }

    public SettingsFBOutDto toDto(@NonNull final SettingsFB entity) {
        return new SettingsFBOutDto()
                .setSettingsId(entity.getSettingsId())
                .setName(entity.getName())
                .setCaseSensitive(entity.isCaseSensitive())
                .setTypoAllowed(entity.isTypoAllowed())
                .setNumeric(entity.isNumeric())
                .setWordsLimit(entity.getWordsLimit())
                .setSymbolsLimit(entity.getSymbolsLimit())
                .setLang(languageDtoTransformer.toDto(entity.getLang()))
                .setStaff(staffMinMapper.toDto(entity.getStaff()));
    }
}
