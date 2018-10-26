package ua.edu.ratos.service.session;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.dto.session.BatchIn;
import ua.edu.ratos.service.dto.session.OptionsDto;
import ua.edu.ratos.service.session.domain.BatchEvaluated;
import ua.edu.ratos.service.session.domain.MetaData;
import ua.edu.ratos.service.session.domain.ResponseEvaluated;
import ua.edu.ratos.service.session.domain.SessionData;

import java.util.Map;

@Service
public class MetaDataService {

    /**
     * Usage : when all is skipped, no evaluated questions
     * @param sessionData
     * @param batchIn
     */
    public void update(@NonNull SessionData sessionData, @NonNull final BatchIn batchIn) {
        // evaluate options map
        updateOptions(sessionData, batchIn.getOptions());
    }

    /**
     * Usage : when some evaluated and some options ticked
     * @param sessionData
     * @param batchEvaluated
     */
    public void update(@NonNull SessionData sessionData, @NonNull final BatchEvaluated batchEvaluated) {
        // evaluate options map
        updateOptions(sessionData, batchEvaluated.getOptions());
        // additionally evaluate incorrect responses
        updateIncorrect(sessionData, batchEvaluated.getResponsesEvaluated());
    }




    private void updateOptions(@NonNull SessionData sessionData, @NonNull final Map<Long, OptionsDto> options) {
        final Map<Long, MetaData> metaDataMap = sessionData.getMetaData();
        options.forEach((questionId, opt)->{
            MetaData newMetaData = buildMetaData(opt);
            if (!metaDataMap.containsKey(questionId)) {
                metaDataMap.put(questionId, newMetaData);
            } else {
                // extract what is there and update with new values
                final MetaData existingMetaData = metaDataMap.get(questionId);
                existingMetaData.setSkipped((short) (existingMetaData.getSkipped()+newMetaData.getSkipped()));
                existingMetaData.setHelp((short) (existingMetaData.getHelp()+newMetaData.getHelp()));
                existingMetaData.setComplained(newMetaData.isComplained());
                existingMetaData.setStarred(newMetaData.isStarred());
            }
        });
    }

    private void updateIncorrect(@NonNull SessionData sessionData, @NonNull final Map<Long, ResponseEvaluated> responsesEvaluated) {
        final Map<Long, MetaData> metaDataMap = sessionData.getMetaData();
        responsesEvaluated.forEach((questionId, responseEvaluated)->{
            if (responseEvaluated.getScore()<100) {
                // do logic here for incorrect
                MetaData newMetaData = new MetaData().setIncorrect((short)1);
                if (!metaDataMap.containsKey(questionId)) {
                    metaDataMap.put(questionId, newMetaData);
                } else {
                    // extract what is there and add 1
                    final MetaData existingMetaData = metaDataMap.get(questionId);
                    existingMetaData.setIncorrect((short) (existingMetaData.getIncorrect()+1));
                }
            }
        });
    }

    private MetaData buildMetaData(@NonNull final OptionsDto options) {
        return new MetaData()
                .setSkipped(options.isSkipRequested() ? (short) 1 : 0)
                .setHelp(options.isHelpRequested() ? (short)1 :0)
                .setComplained(options.isComplainRequested())
                .setStarred(options.isStarRequested());
    }
}
