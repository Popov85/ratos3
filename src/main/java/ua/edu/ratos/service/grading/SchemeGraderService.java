package ua.edu.ratos.service.grading;

import ua.edu.ratos.service.NamedService;

public interface SchemeGraderService extends NamedService<Long> {

    void save(long schemeId, long gradingDetailsId);

    void delete(long schemeId);

    /**
     * Finds one of the {FourPointGradingOutDto, FreePointGradingOutDto, TwoPointGradingOutDto}
     * for frontend serialization purposes;
     * @param schemeId schemeId
     * @return One of the *PointGrading-s
     */
    Object findDetails(long schemeId);
}
