package ua.edu.ratos.dao.repository.specs;

import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;
import ua.edu.ratos.dao.entity.*;
import javax.persistence.criteria.*;

/**
 * https://stackoverflow.com/questions/30630642/querying-a-nullable-onetoone-relationship-with-jpa
 */
public class ResultOfStudentStaffSpecs {

    public static Specification<ResultOfStudent> ofDepartment(@NonNull final Long depId) {
        return (Specification<ResultOfStudent>) (root, query, builder) -> {
            Path<Department> departmentPath = root.get(ResultOfStudent_.department);
            if (Long.class != query.getResultType()) {
                root.fetch(ResultOfStudent_.scheme.getName(), JoinType.LEFT)
                        .fetch(Scheme_.course.getName(), JoinType.LEFT);
                Fetch<Object, Object> fs = root.fetch(ResultOfStudent_.student.getName(), JoinType.LEFT);
                        fs.fetch(Student_.user.getName(), JoinType.LEFT);
                        fs.fetch(Student_.studentClass.getName(), JoinType.LEFT);
                        fs.fetch(Student_.faculty.getName(), JoinType.LEFT);
                        fs.fetch(Student_.organisation.getName(), JoinType.LEFT);
                root.fetch(ResultOfStudent_.department.getName(), JoinType.LEFT);
            }
            return builder.equal(departmentPath.get(Department_.depId), depId);
        };
    }
}
