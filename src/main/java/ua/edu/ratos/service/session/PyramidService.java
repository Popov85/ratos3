package ua.edu.ratos.service.session;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.domain.BatchEvaluated;
import ua.edu.ratos.service.domain.SessionData;
import java.util.List;

/**
 * Pyramid mode implies that in case when it is enabled,
 * we shift incorrectly answered questions to the end of the questions
 * list, just like we did with skipped questions;
 */
@Service
public class PyramidService {

    private static final String WRONG_API_USAGE = "Wrong API usage! You invoke pyramid processing for a scheme with disabled pyramid mode!";

    private ShiftService shiftService;

    @Autowired
    public void setShiftService(ShiftService shiftService) {
        this.shiftService = shiftService;
    }

    /**
     * Process Pyramid mode: move incorrect responses to the end of the list
     * Make sure to call this method only if Pyramid mode is enabled
     * @param sessionData
     * @param batchEvaluated
     */
    public void process(@NonNull final SessionData sessionData, @NonNull final BatchEvaluated batchEvaluated) {
        if (!sessionData.getSchemeDomain().getModeDomain().isPyramid())
            throw new RuntimeException(WRONG_API_USAGE);
        List<Long> incorrect = batchEvaluated.getIncorrectResponseIds();
        if (!incorrect.isEmpty()) shiftService.doShift(incorrect, sessionData);
    }
}
