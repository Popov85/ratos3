package ua.edu.ratos.service.session.sequence;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.config.TrackTime;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.entity.SchemeTheme;
import ua.edu.ratos.dao.entity.SchemeThemeSettings;
import ua.edu.ratos.dao.repository.QuestionRepository;
import ua.edu.ratos.dao.repository.SchemeRepository;

import java.util.List;
import java.util.Set;

/**
 * Used to speed up a session's start time for a scheme by putting all its questions into L2C
 */
@Slf4j
@Component
public class L2CSessionSupport {

    private SchemeRepository schemeRepository;

    private QuestionRepository questionRepository;

    @Autowired
    public void setSchemeRepository(SchemeRepository schemeRepository) {
        this.schemeRepository = schemeRepository;
    }

    @Autowired
    public void setQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Async
    @TrackTime
    @Transactional(readOnly = true)
    public void loadAllSchemesQuestionsToL2C(@NonNull final Long schemeId) {
        Scheme scheme = schemeRepository.findForSessionById(schemeId);
        List<SchemeTheme> schemeThemes = scheme.getThemes();
        for (SchemeTheme schemeTheme : schemeThemes) {
            Long themeId = schemeTheme.getTheme().getThemeId();
            Set<SchemeThemeSettings> settings = schemeTheme.getSettings();
            for (SchemeThemeSettings setting : settings) {
                Long typeId = setting.getType().getTypeId();
                if (setting.getLevel1()!=0) loadToL2C(themeId, typeId, (byte)1);
                if (setting.getLevel2()!=0) loadToL2C(themeId, typeId, (byte)2);
                if (setting.getLevel2()!=0) loadToL2C(themeId, typeId, (byte)3);
            }
        }
        log.debug("Finished loading questions of schemeId = {} to L2C", schemeId);
    }

    private void loadToL2C(Long themeId, Long typeId, byte level) {
        if (typeId.equals(1L)) {
            questionRepository.findAllMCQForCacheWithEverythingByThemeId(themeId, level); return;
        }
        if (typeId.equals(2L)) {
            questionRepository.findAllMCQForCacheWithEverythingByThemeId(themeId, level); return;
        }
        if (typeId.equals(3L)) {
            questionRepository.findAllMCQForCacheWithEverythingByThemeId(themeId, level); return;
        }
        if (typeId.equals(4L)) {
            questionRepository.findAllMCQForCacheWithEverythingByThemeId(themeId, level); return;
        }
        if (typeId.equals(5L)) {
            questionRepository.findAllMCQForCacheWithEverythingByThemeId(themeId, level); return;
        }
        log.error("Failed to load questions to L2C, themeId = {}, typeId = {}, level = {}", themeId, typeId, level);
        throw new RuntimeException("Invalid question type Id, failed to load questions to L2C");
    }
}
