package ua.edu.ratos.service.session;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.domain.SessionData;

@Service
public class BatchCreatingDataProducer {

    public int getQuestionsLeft(@NonNull final SessionData sessionData, int questionsPerBatch) {
        final int currentIndex = sessionData.getCurrentIndex();
        final int totalQuestionsNumber = sessionData.getSequence().size();
        int questionsLeft = totalQuestionsNumber-(currentIndex+questionsPerBatch);
        return questionsLeft;
    }

    public int getBatchesLeft(@NonNull final SessionData sessionData, int questionsPerBatch) {
        final int currentIndex = sessionData.getCurrentIndex()+questionsPerBatch;
        final int totalQuestionsNumber = sessionData.getSequence().size();
        if (currentIndex>=totalQuestionsNumber) return 0;
        int quantity = (totalQuestionsNumber - currentIndex)/questionsPerBatch;
        if ((totalQuestionsNumber - currentIndex)%questionsPerBatch==0) {
            return quantity;
        } else {
            return ++quantity;
        }
    }
}
