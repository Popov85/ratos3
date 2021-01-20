package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.SettingsFB;
import ua.edu.ratos.service.dto.out.SettingsFBOutDto;
import ua.edu.ratos.service.transformer.LanguageMapper;
import ua.edu.ratos.service.transformer.StaffMinMapper;

@Deprecated
@Component
public class SettingsFBDtoTransformer {

    private LanguageMapper languageMapper;

    private StaffMinMapper staffMinMapper;

    @Autowired
    public void setLanguageDtoTransformer(LanguageMapper languageMapper) {
        this.languageMapper = languageMapper;
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
                .setLang(languageMapper.toDto(entity.getLang()))
                .setStaff(staffMinMapper.toDto(entity.getStaff()));
    }
}
