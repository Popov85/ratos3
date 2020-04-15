package ua.edu.ratos.service.grading;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class SchemeGradingManagerService {

    private final SchemeGradingServiceFactory gradingServiceFactory;

    /**
     *  1. Get a correct Scheme*Service based on gradingId
     *  2. Invoke service.doGameProcessing(schemeId, gradingDetailsId);
     * @param schemeId
     * @param gradingId
     * @param gradingDetailsId
     */
    public Object save(long schemeId, long gradingId, long gradingDetailsId) {
        final SchemeGraderService schemeGraderService = gradingServiceFactory.getInstance(gradingId);
        return schemeGraderService.save(schemeId, gradingDetailsId);
    }

    public void remove(long schemeId, long gradingId) {
        final SchemeGraderService graderService = gradingServiceFactory.getInstance(gradingId);
        graderService.delete(schemeId);
    }

    public Object findDetails(long schemeId, long gradingId) {
        final SchemeGraderService schemeGraderService = gradingServiceFactory.getInstance(gradingId);
        return schemeGraderService.findDetails(schemeId);
    }
}
