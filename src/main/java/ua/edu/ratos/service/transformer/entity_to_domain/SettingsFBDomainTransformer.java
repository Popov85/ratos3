package ua.edu.ratos.service.transformer.entity_to_domain;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.SettingsFB;
import ua.edu.ratos.service.domain.SettingsFBDomain;

@Slf4j
@Component
public class SettingsFBDomainTransformer {

    public SettingsFBDomain toDomain(@NonNull final SettingsFB s) {
        return new SettingsFBDomain()
                .setSettingsId(s.getSettingsId())
                .setName(s.getName())
                .setWordsLimit(s.getWordsLimit())
                .setSymbolsLimit(s.getSymbolsLimit())
                .setNumeric(s.isNumeric())
                .setLang(s.getLang().getAbbreviation())
                .setTypoAllowed(s.isTypoAllowed())
                .setCaseSensitive(s.isCaseSensitive());
    }

}
