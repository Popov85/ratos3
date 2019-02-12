package ua.edu.ratos.dao.repository.criteria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.edu.ratos.dao.entity.ResultOfStudent;

public interface ResultOfStudentCriteriaRepository {

    Page<ResultOfStudent> findAllByCriteria(ResultOfStudentCriteriaInDto criteria, Pageable pageable);
}
