package ua.edu.ratos.service.grading;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SchemeGradingManagerService {

    @Autowired
    private SchemeGradingServiceFactory gradingServiceFactory;

    /**
     *  1. Get a correct SchemeDomain*Service based on gradingDomain Id
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
