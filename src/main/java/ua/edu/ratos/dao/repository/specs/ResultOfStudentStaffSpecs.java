package ua.edu.ratos.dao.repository.specs;

import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;
import ua.edu.ratos.dao.entity.*;
import ua.edu.ratos.service.dto.in.criteria.results.ResultOfStudentCriteriaStaffInDto;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * https://stackoverflow.com/questions/30630642/querying-a-nullable-onetoone-relationship-with-jpa
 */
public class ResultOfStudentStaffSpecs {

    //--------------------------------------------------DEP spec--------------------------------------------------------

    public static Specification<ResultOfStudent> ofDepartment(@NonNull final Long depId) {
        return (Specification<ResultOfStudent>) (root, query, builder) -> {
            Path<Department> departmentPath = root.get(ResultOfStudent_.department);
            return builder.equal(departmentPath.get(Department_.depId), depId);
        };
    }

    //---------------------------------------------MAIN specs for staff-------------------------------------------------

    public static Specification<ResultOfStudent> hasSpecs(@NonNull final ResultOfStudentCriteriaStaffInDto criteria) {
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

            // strings
            if (criteria.getSurname()!=null && !criteria.getSurname().isEmpty()) {
                String surnameCriteria = criteria.getSurname() + "%";
                if (criteria.isContains()) surnameCriteria = "%" + surnameCriteria;
                Predicate surnamePredicate = builder.like(root.get(ResultOfStudent_.student).get(Student_.user).get(User_.surname), surnameCriteria);
                predicates.add(surnamePredicate);
            }

            return builder.and(predicates.toArray(new Predicate[] {}));
        };
    }

}
