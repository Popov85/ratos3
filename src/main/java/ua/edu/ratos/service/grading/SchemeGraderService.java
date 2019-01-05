package ua.edu.ratos.service.grading;

public interface SchemeGraderService {
    void save(long schemeId, long gradingDetailsId);
    void delete(long schemeId);
    long type();
}
