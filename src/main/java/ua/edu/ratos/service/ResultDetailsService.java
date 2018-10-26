package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.domain.entity.ResultDetails;
import ua.edu.ratos.domain.repository.ResultDetailsRepository;
import ua.edu.ratos.domain.repository.ResultRepository;
import ua.edu.ratos.service.session.domain.SessionData;
import ua.edu.ratos.service.session.serializer.SessionDataSerializer;

import java.time.LocalDateTime;

@Service
public class ResultDetailsService {

    @Autowired
    private ResultDetailsRepository resultDetailsRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private SessionDataSerializer serializer;

    public Long save(@NonNull final SessionData sessionData, @NonNull final Long resultId) {

        ResultDetails resultDetails = new ResultDetails();
        resultDetails.setDetailId(resultId);
        resultDetails.setResult(resultRepository.getOne(resultId));
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
