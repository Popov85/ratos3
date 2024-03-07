package ua.edu.ratos.dao.repository.projections;

/**
 * This projection is used in QuestionRepository to support scheme creating
 */
public interface TypeAndCount {
    Long getType();
    String getAbbreviation();
    Integer getCount();
}
