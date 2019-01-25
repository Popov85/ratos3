package ua.edu.ratos.service.session;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.domain.SettingsDomain;

@Slf4j
@Service
public class LevelsEvaluatorService {

    /**
     * Process level 2 and level 3 questions;
     * Just multiply the score by the corresponding coefficient;
     * Level 1 and 2 coefficients are within 1-1.99
     * @param score
     * @param level
     * @param settingsDomain
     * @return multiplied score
     */
    public double evaluateLevels(final double score, final byte level, @NonNull final SettingsDomain settingsDomain) {
        if (level==2) {
            return score* settingsDomain.getLevel2Coefficient();
        } else if (level==3) {
            return score* settingsDomain.getLevel3Coefficient();
        } else {
            log.warn("Unrecognized level {}", level, ", fallback, level 1 is accepted");
            return score;
        }
    }
}
