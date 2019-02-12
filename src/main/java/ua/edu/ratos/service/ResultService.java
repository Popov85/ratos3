package ua.edu.ratos.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.*;
import ua.edu.ratos.dao.entity.lms.LMS;
import ua.edu.ratos.dao.repository.ResultRepository;
import ua.edu.ratos.service.domain.ResultDomain;
import ua.edu.ratos.service.domain.SessionData;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@Slf4j
@Service
public class ResultService {

    @PersistenceContext
    private EntityManager em;

    private ResultRepository resultRepository;

    @Autowired
    public void setResultRepository(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    @Transactional
    public Long save(@NonNull final SessionData sessionData, @NonNull final ResultDomain resultDomain, boolean timeOuted) {
        Result r = new Result();
        Scheme scheme = em.find(Scheme.class, sessionData.getSchemeDomain().getSchemeId());
        r.setScheme(scheme);
        r.setUser(em.getReference(User.class, sessionData.getUserId()));
        r.setDepartment(scheme.getDepartment());
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
