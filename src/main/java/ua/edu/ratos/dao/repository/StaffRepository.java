package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.Staff;

public interface StaffRepository extends JpaRepository<Staff, Long> {

    @Query(value = "SELECT s FROM Staff s join fetch s.user u left join fetch u.roles join fetch s.department d join fetch d.faculty f join fetch f.organisation o where u.email = ?1")
    Staff findByIdForAuthentication(String email);
}
