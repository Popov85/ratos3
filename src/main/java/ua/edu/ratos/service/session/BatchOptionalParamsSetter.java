package ua.edu.ratos.service.session;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.domain.OptionsDomain;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.domain.SettingsDomain;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
@AllArgsConstructor
public class BatchOptionalParamsSetter {

    private final ProgressDataService progressDataService;

    /**
     * Sets optional param of questionsLeft if allowed by settings
     * @param sessionData
     * @param batchOutDto
     * @param questionsLeft
     */
    public void setQuestionsLeft(@NonNull final SessionData sessionData, @NonNull final BatchOutDto batchOutDto, int questionsLeft) {
        OptionsDomain opt = sessionData.getSchemeDomain().getOptionsDomain();
        if (opt.isDisplayQuestionsLeft()) {
            batchOutDto.setQuestionsLeft(questionsLeft);
        }
    }

    /**
     * Sets optional param of batchesLeft if allowed by settings
     * @param sessionData
     * @param batchOutDto
     * @param batchesLeft
     */
    public void setBatchesLeft(@NonNull final SessionData sessionData, @NonNull final BatchOutDto batchOutDto, int batchesLeft) {
        OptionsDomain opt = sessionData.getSchemeDomain().getOptionsDomain();
        if (opt.isDisplayBatchesLeft()) {
            batchOutDto.setBatchesLeft(batchesLeft);
        }
    }

    /**
     * Sets more optional params if any for a batch:
     * sessionExpiresInSec, batchExpiresInSec, currentScore, effectiveScore, progress, motivationalMessage, etc.
     * @param sessionData SessionData
     * @param batchOutDto which batch to set optional params
     */
    public void setOptionalParams(@NonNull final SessionData sessionData, @NonNull final BatchOutDto batchOutDto) {
        //1) TimeLeft
        if (sessionData.isSessionTimeLimited()) {
            batchOutDto.setSessionExpiresInSec(getSessionExpiresInSec(sessionData));
        }
        //2) BatchTimeLimited
        if (sessionData.isSessionTimeLimited() && sessionData.isBatchTimeLimited()) {
            batchOutDto.setBatchExpiresInSec(getBatchExpiresInSec(sessionData, batchOutDto.getQuestions().size()));
        }
        OptionsDomain opt = sessionData.getSchemeDomain().getOptionsDomain();
        //3) CurrentScore
        if (opt.isDisplayCurrentScore()) {
            batchOutDto.setCurrentScore(getCurrentScore(sessionData));
        }
        //4) EffectiveScore
        if (opt.isDisplayEffectiveScore()) {
            batchOutDto.setEffectiveScore(getEffectiveScore(sessionData));
        }
        //5) Progress
        if (opt.isDisplayProgress()) {
            batchOutDto.setProgress(getProgress(sessionData));
        }
    }

    private Long getSessionExpiresInSec(@NonNull final SessionData sessionData) {
        if (sessionData.isSingleBatchSession()) {
            // If session consists of a single big batch
            // It needs to be consistent with batchTimeLimit
            SettingsDomain s = sessionData.getSchemeDomain().getSettingsDomain();
            long perQuestionTimeLimit = s.getSecondsPerQuestion();
            short questionsPerSheet = s.getQuestionsPerSheet();
            return perQuestionTimeLimit * questionsPerSheet;
        }
        final LocalDateTime sessionTimeout = sessionData.getSessionTimeout();
        long timeLeft = LocalDateTime.now().until(sessionTimeout, ChronoUnit.SECONDS);
        return timeLeft;
    }

    /**
     * Calculate seconds until the current batch expires
     * @param sessionData SessionData
     * @param questionsInBatch it really varies for dynamic sessions
     * @return seconds till current batch expires
     */
    private Long getBatchExpiresInSec(@NonNull final SessionData sessionData, int questionsInBatch) {
        long perQuestionTimeLimit = sessionData.getSchemeDomain().getSettingsDomain().getSecondsPerQuestion();
        long batchTimeLeft = perQuestionTimeLimit * questionsInBatch;
        return batchTimeLeft;
    }

    private String getCurrentScore(@NonNull final SessionData sessionData) {
        double currentActualScore = progressDataService.getCurrentActualScore(sessionData);
        // Do not expose numbers > 100 which is possible due to bounties
        if (currentActualScore> 100) return "100.0";
        return this.round(currentActualScore);
    }

    private String getEffectiveScore(@NonNull final SessionData sessionData) {
        double effectiveActualScore = progressDataService.getEffectiveActualScore(sessionData);
        // Do not expose numbers > 100 which is possible due to bounties
        if (effectiveActualScore> 100) return "100.0";
        return this.round(effectiveActualScore);
    }

    private String getProgress(@NonNull final SessionData sessionData) {
        double progress = progressDataService.getProgress(sessionData)*100d;
        return this.round(progress);
    }

    private String round(Double value) {
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(value);
    }
}
