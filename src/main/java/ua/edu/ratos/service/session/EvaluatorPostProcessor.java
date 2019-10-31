package ua.edu.ratos.service.session;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.edu.ratos.config.properties.AppProperties;
import ua.edu.ratos.service.domain.SettingsDomain;

/**
 * We add bounties and penalties only after the response is fully evaluated, "post factum",
 * so that not to mix partial correct results and penalties/bounties
 */
@Slf4j
@Service
@AllArgsConstructor
public class EvaluatorPostProcessor {

    private final AppProperties appProperties;

    /**
     * Applicable to individual question results only;
     * Process level 2 and level 3 questions;
     * Just multiply the score by the corresponding coefficient;
     * Level 1 and 2 coefficients are within 1-1.99
     * @param score
     * @param level
     * @param l2
     * @param l3
     * @return score after bounty
     */
    public double applyBounty(double score, final byte level, float l2, float l3) {
        if (level==1) {
            return score;
        } else if (level==2) {
            log.debug("Applied bounty of = {} to score = {}", l2, score);
            return score* l2;
        } else if (level==3) {
            log.debug("Applied bounty of = {} to score = {}", l3, score);
            return score* l3;
        } else {
            log.warn("Unrecognized level {}", level, ", fallback, level 1 is accepted");
            return score;
        }
    }

    /**
     * Applicable to batch or individual question results
     * @param score
     * @return score after penalty
     */
    public double applyPenalty(double score) {
        // % of the total score to be distracted
        double penalty = appProperties.getSession().getTimeoutPenalty();
        // Just for visualization)
        if (score==0) return 0;
        if (penalty==0) return score;
        if (penalty==100) return 0;
        log.debug("Applied penalty of = {} % to score = {}", penalty, score);
        return score-score*(penalty/100);
    }

    public Double getBounty(byte level, SettingsDomain settingsDomain) {
        float l2 = settingsDomain.getLevel2Coefficient();
        float l3 = settingsDomain.getLevel3Coefficient();
        // 1.5 -> 50.0 (%)
        if (level==2) {
            return (l2-1)*100d;
        }
        if (level==3) {
            return (l3-1)*100d;
        }
        return new Double(0);
    }

    public Double getPenalty() {
        return appProperties.getSession().getTimeoutPenalty();
    }
}
