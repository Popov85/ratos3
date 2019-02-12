package ua.edu.ratos.dao.repository.criteria;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ua.edu.ratos.dao.entity.ResultOfStudent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class ResultOfStudentCriteriaRepositoryImpl implements ResultOfStudentCriteriaRepository {

    @PersistenceContext
    private EntityManager em;


    @Override
    public Page<ResultOfStudent> findAllByCriteria(ResultOfStudentCriteriaInDto criteria, Pageable pageable) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ResultOfStudent> cq = cb.createQuery(ResultOfStudent.class);
        Root<ResultOfStudent> root = cq.from(ResultOfStudent.class);
        root.alias("results");// Needed for count query https://stackoverflow.com/questions/30124983/select-count-with-where-using-criteriabuilder

        // Actually works faster without fetching!

        /*root.fetch("scheme", JoinType.INNER);
        root.fetch("student", JoinType.INNER).fetch("user", JoinType.INNER);*/

        List<Predicate> predicates = new ArrayList();

        // objects
        if (criteria.getCourseId()!=null && criteria.getCourseId()>0) {
            Predicate coursePredicate = cb.equal(root.get("scheme").get("course").get("courseId"), criteria.getCourseId());
            predicates.add(coursePredicate);
        }
        if (criteria.getSchemeId()!=null && criteria.getSchemeId()>0) {
            Predicate schemePredicate = cb.equal(root.get("scheme").get("schemeId"), criteria.getSchemeId());
            predicates.add(schemePredicate);
        }

        // dates
        if (criteria.getResultsFrom()!=null && criteria.getResultsTo()==null) {
            Predicate fromPredicate = cb.greaterThan(root.get("sessionEnded"), criteria.getResultsFrom());
            predicates.add(fromPredicate);
        }
        if (criteria.getResultsFrom()!=null && criteria.getResultsTo()!=null) {
            Predicate betweenPredicate = cb.and
                    (cb.greaterThan(root.get("sessionEnded"), criteria.getResultsFrom()), cb.lessThan(root.get("sessionEnded"), criteria.getResultsTo()));
            predicates.add(betweenPredicate);
        }
        if (criteria.getResultsFrom()==null && criteria.getResultsTo()!=null) {
            Predicate toPredicate = cb.lessThan(root.get("sessionEnded"), criteria.getResultsTo());
            predicates.add(toPredicate);
        }

        // strings
        if (criteria.getName()!=null && !criteria.getName().isEmpty()) {
            Predicate namePredicate = cb.like(root.get("student").get("user").get("name"), "%" + criteria.getName() + "%");
            predicates.add(namePredicate);
        }
        if (criteria.getSurname()!=null && !criteria.getSurname().isEmpty()) {
            Predicate surnamePredicate = cb.like(root.get("student").get("user").get("surname"), "%" + criteria.getSurname() + "%");
            predicates.add(surnamePredicate);
        }
        cq.where(predicates.toArray(new Predicate[]{}));


        // ordering
        Sort sort = pageable.getSort();
        List<Order> orderList = new ArrayList();
        sort.forEach(s->{
            String property = s.getProperty();
            Sort.Direction direction = s.getDirection();
            log.debug("Property = {}, direction = {}", property, direction);
            if (direction.isAscending()) {
                orderList.add(cb.asc(root.get(property)));
            } else {
                orderList.add(cb.desc(root.get(property)));
            }
        });
        cq.orderBy(orderList);


        // count query
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<ResultOfStudent> countRoot = countQuery.from(ResultOfStudent.class);
        countRoot.alias("results");
        Long totalRows =
                em.createQuery(countQuery.select(cb.count(root)).where(predicates.toArray(new Predicate[] {})))
                  .getSingleResult();


        // pagination processing
        TypedQuery<ResultOfStudent> query = em.createQuery(cq);
        int pageSize = pageable.getPageSize();
        int firstResult = pageable.getPageNumber() * pageSize;
        query.setFirstResult(firstResult);
        query.setMaxResults(pageSize);

        return new PageImpl(query.getResultList(), pageable, totalRows);
    }
}
