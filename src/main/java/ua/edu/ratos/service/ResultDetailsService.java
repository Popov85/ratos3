package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.config.TrackTime;
import ua.edu.ratos.dao.entity.Result;
import ua.edu.ratos.dao.entity.ResultDetails;
import ua.edu.ratos.dao.repository.ResultDetailsRepository;
import ua.edu.ratos.service.session.domain.SessionData;
import ua.edu.ratos.service.session.SessionDataSerializerService;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Service
public class ResultDetailsService {

    @Autowired
    private ResultDetailsRepository resultDetailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private SessionDataSerializerService serializer;

    @TrackTime
    @Transactional
    public Long save(@NonNull final SessionData sessionData, @NonNull final Long resultId) {
        ResultDetails resultDetails = new ResultDetails();
        // no resultDetails.getDetailId() should be set, it cases exception
        resultDetails.setResult(em.getReference(Result.class, resultId));
        resultDetails.setWhenRemove(calculateWhenRemove(sessionData));
        resultDetails.setJsonData(serializer.serialize(sessionData));
        resultDetailsRepository.save(resultDetails);
        return resultDetails.getDetailId();
    }

    private LocalDateTime calculateWhenRemove(@NonNull final SessionData sessionData) {
        final short daysKeepResultDetails = sessionData
                .getScheme()
                .getSettings()
                .getDaysKeepResultDetails();
        return LocalDateTime.now().plusDays(daysKeepResultDetails);
    }
}
