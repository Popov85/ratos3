package ua.edu.ratos.dao.repository.specs;

import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;
import ua.edu.ratos.dao.entity.*;
import ua.edu.ratos.service.dto.in.criteria.results.ResultOfStudentCriteriaSelfInDto;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * https://stackoverflow.com/questions/30630642/querying-a-nullable-onetoone-relationship-with-jpa
 */
public class ResultOfStudentSelfSpecs {

    //--------------------------------------------------Stud spec-------------------------------------------------------

    public static Specification<ResultOfStudent> byStudent(@NonNull final Long studId) {
        return (Specification<ResultOfStudent>) (root, query, builder) -> {
            Path<Student> studentPath = root.get(ResultOfStudent_.student);
            return builder.equal(studentPath.get(Student_.studId), studId);
        };
    }

    //---------------------------------------------MAIN specs for student-----------------------------------------------

    public static Specification<ResultOfStudent> hasSpecs(@NonNull final ResultOfStudentCriteriaSelfInDto criteria) {
        return (Specification<ResultOfStudent>) (root, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();

            // objects
            if (criteria.getCourseId()!=null && criteria.getCourseId()>0) {
                Predicate coursePredicate = builder.equal(root.get(ResultOfStudent_.scheme).get(Scheme_.course).get(Course_.courseId), criteria.getCourseId());
                predicates.add(coursePredicate);
            }
            if (criteria.getSchemeId()!=null && criteria.getSchemeId()>0) {
                Predicate schemePredicate = builder.equal(root.get(ResultOfStudent_.scheme).get(Scheme_.schemeId), criteria.getSchemeId());
                predicates.add(schemePredicate);
            }

            // flags
            if (criteria.isLms()) {
                // Only LMS courses
                Predicate coursePredicate = builder.isTrue(root.join(ResultOfStudent_.scheme).join(Scheme_.course).join(Course_.lmsCourse, JoinType.LEFT).isNotNull());
                predicates.add(coursePredicate);
            }

            // dates
            if (criteria.getResultsFrom()!=null && criteria.getResultsTo()==null) {
                Predicate fromPredicate = builder.greaterThan(root.get(ResultOfStudent_.sessionEnded), criteria.getResultsFrom());
                predicates.add(fromPredicate);
            }
            if (criteria.getResultsFrom()!=null && criteria.getResultsTo()!=null) {
                Predicate betweenPredicate = builder
                        .and(builder.greaterThan(root.get(ResultOfStudent_.sessionEnded), criteria.getResultsFrom()), builder.lessThan(root.get(ResultOfStudent_.sessionEnded), criteria.getResultsTo()));
                predicates.add(betweenPredicate);
            }
            if (criteria.getResultsFrom()==null && criteria.getResultsTo()!=null) {
                Predicate toPredicate = builder.lessThan(root.get(ResultOfStudent_.sessionEnded), criteria.getResultsTo());
                predicates.add(toPredicate);
            }

            return builder.and(predicates.toArray(new Predicate[] {}));
        };
    }

}
