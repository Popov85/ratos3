package ua.edu.ratos.service.grading;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SchemeGradingManagerService {

    private SchemeGradingServiceFactory gradingServiceFactory;

    @Autowired
    public void setGradingServiceFactory(SchemeGradingServiceFactory gradingServiceFactory) {
        this.gradingServiceFactory = gradingServiceFactory;
    }

    /**
     *  1. Get a correct Scheme*Service based on gradingId
     *  2. Invoke service.save(schemeId, gradingDetailsId);
     * @param schemeId
     * @param gradingId
     * @param gradingDetailsId
     */
    public void save(long schemeId, long gradingId, long gradingDetailsId) {
        final SchemeGraderService graderService = gradingServiceFactory.getGraderService(gradingId);
        graderService.save(schemeId, gradingDetailsId);
    }

    public void remove(long schemeId, long gradingId) {
        final SchemeGraderService graderService = gradingServiceFactory.getGraderService(gradingId);
        graderService.delete(schemeId);
    }
}
