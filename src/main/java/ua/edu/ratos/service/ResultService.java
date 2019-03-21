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
    public Long save(@NonNull final ResultDomain resultDomain) {
        Result r = new Result();
        Scheme scheme = em.find(Scheme.class, resultDomain.getScheme().getSchemeId());
        r.setScheme(scheme);
        r.setUser(em.getReference(User.class, resultDomain.getUser().getUserId()));
        r.setDepartment(scheme.getDepartment());
        if (resultDomain.getLmsId().isPresent())
            r.setLms(em.getReference(LMS.class, resultDomain.getLmsId().get()));
        r.setPassed(resultDomain.isPassed());
        r.setPoints(resultDomain.hasPoints());
        r.setGrade(resultDomain.getGrade());
        r.setPercent(resultDomain.getPercent());
        r.setSessionLasted(resultDomain.getTimeSpent());
        r.setSessionEnded(LocalDateTime.now());
        r.setTimeOuted(resultDomain.isTimeOuted());
        r.setCancelled(resultDomain.isCancelled());
        resultDomain.getThemeResults().forEach(t -> {
            Theme theme = em.getReference(Theme.class, t.getTheme().getThemeId());
            r.addResultTheme(theme, t.getPercent(), t.getQuantity());
        });
        resultRepository.save(r);

        return r.getResultId();
    }
}
