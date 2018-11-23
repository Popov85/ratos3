package ua.edu.ratos.service.session;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.session.domain.MetaData;
import ua.edu.ratos.service.session.domain.SessionData;
import java.util.List;
import java.util.Map;

@Service
public class MetaDataService {

    public void createOrUpdateHelp(@NonNull SessionData sessionData, @NonNull final Long questionId) {
        final Map<Long, MetaData> metaDataMap = sessionData.getMetaData();
        if (!metaDataMap.containsKey(questionId)) {
            metaDataMap.put(questionId, new MetaData().setHelp((short)1));
        } else {
            MetaData metaData = metaDataMap.get(questionId);
            short help = metaData.getHelp();
            metaData.setHelp(++help);
        }
    }

    public void createOrUpdateSkip(@NonNull SessionData sessionData, @NonNull final Long questionId) {
        final Map<Long, MetaData> metaDataMap = sessionData.getMetaData();
        if (!metaDataMap.containsKey(questionId)) {
            metaDataMap.put(questionId, new MetaData().setSkipped((short)1));
        } else {
            MetaData metaData = metaDataMap.get(questionId);
            short skipped = metaData.getSkipped();
            metaData.setSkipped(++skipped);
        }
    }

    public void createOrUpdateStar(@NonNull SessionData sessionData, @NonNull final Long questionId, final byte star) {
        final Map<Long, MetaData> metaDataMap = sessionData.getMetaData();
        if (!metaDataMap.containsKey(questionId)) {
            metaDataMap.put(questionId, new MetaData().setStarred(star));
        } else {
            MetaData metaData = metaDataMap.get(questionId);
            metaData.setStarred(star);
        }
    }

    public void createOrUpdateIncorrect(@NonNull SessionData sessionData, @NonNull final List<Long> incorrectIds) {
        final Map<Long, MetaData> metaDataMap = sessionData.getMetaData();
        for (Long questionId : incorrectIds) {
            if (!metaDataMap.containsKey(questionId)) {
                metaDataMap.put(questionId, new MetaData().setIncorrect((short)1));
            } else {
                MetaData metaData = metaDataMap.get(questionId);
                short incorrect = metaData.getIncorrect();
                metaData.setIncorrect(++incorrect);
            }
        }
    }
}
