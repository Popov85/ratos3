package ua.edu.ratos.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.domain.entity.Help;

/**
 * @link https://stackoverflow.com/questions/21549480/spring-data-fetch-join-with-paging-is-not-working
 * @link https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/Query.html
 */
public interface HelpRepository extends JpaRepository<Help, Long> {

    @Query(value = "SELECT h FROM Help h join h.staff s left join fetch h.resources where s.staffId=?1 order by h.name desc",
            countQuery = "SELECT count(h) FROM Help h join h.staff s where s.staffId=?1")
    Page<Help> findByStaffIdWithResources(Long staffId, Pageable pageable);

    @Query(value = "SELECT h FROM Help h join h.staff s left join fetch h.resources where s.staffId=?1 and h.name like ?2% order by h.name desc",
            countQuery = "SELECT count(h) FROM Help h join h.staff s where s.staffId=?1 and h.name like ?2%")
    Page<Help> findByStaffIdAndFirstNameLettersWithResources(Long staffId, String starts, Pageable pageable);

    @Query(value = "SELECT h FROM Help h join h.staff s join s.department d left join fetch h.resources where d.depId=?1 order by h.name desc",
            countQuery = "SELECT count(h) FROM Help h join h.staff s join s.department d where d.depId=?1")
    Page<Help> findByDepartmentIdWithResources(Long depId, Pageable pageable);

    @Query(value = "SELECT h FROM Help h join h.staff s join s.department d left join fetch h.resources where d.depId=?1 and h.name like ?2% order by h.name desc",
            countQuery = "SELECT count(h) FROM Help h join h.staff s join s.department d where d.depId=?1 and h.name like ?2%")
    Page<Help> findByDepartmentIdAndFirstNameLettersWithResources(Long depId, String starts, Pageable pageable);

}
