package ua.edu.ratos.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.entity.Theme;
import ua.edu.ratos.dao.entity.User;
import ua.edu.ratos.dao.entity.lms.LMS;
import ua.edu.ratos.dao.repository.ResultRepository;
import ua.edu.ratos.service.domain.ResultDomain;
import ua.edu.ratos.service.domain.SessionData;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Slf4j
@Service
public class ResultService {

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private EntityManager em;

    @Transactional
    public Long save(@NonNull final SessionData sessionData, @NonNull final ResultDomain resultDomain, boolean timeOuted) {
        ua.edu.ratos.dao.entity.Result r = new ua.edu.ratos.dao.entity.Result();
        r.setScheme(em.getReference(Scheme.class, sessionData.getSchemeDomain().getSchemeId()));
        r.setUser(em.getReference(User.class, sessionData.getUserId()));
        if (sessionData.isLMSSession())
            r.setLms(em.getReference(LMS.class, sessionData.getLMSId().get()));
        r.setPassed(resultDomain.isPassed());
        r.setGrade(resultDomain.getGrade());
        r.setPercent(resultDomain.getPercent());
        r.setSessionLasted(sessionData.getProgressData().getTimeSpent());
        r.setSessionEnded(LocalDateTime.now());
        r.setTimeOuted(timeOuted);
        resultDomain.getThemeResults().forEach(t -> {
            Theme theme = em.getReference(Theme.class, t.getThemeId());
            r.addResultTheme(theme, r.getPercent());
        });
        resultRepository.save(r);

        return r.getResultId();
    }
}
