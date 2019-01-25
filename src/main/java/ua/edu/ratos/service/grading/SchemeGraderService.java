package ua.edu.ratos.service.grading;

public interface SchemeGraderService {
    void save(long schemeId, long gradingDetailsId);
    void delete(long schemeId);
    long type();

    /**
     * Finds one of the {FourPointGradingOutDto, FreePointGradingOutDto, TwoPointGradingOutDto}
     * for frontend serialization purposes;
     * @param schemeId schemeId
     * @return One of the *PointGrading-s
     */
    Object findDetails(long schemeId);
}
