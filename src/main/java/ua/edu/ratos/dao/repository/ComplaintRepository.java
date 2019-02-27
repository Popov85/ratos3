package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.Complaint;
import ua.edu.ratos.dao.entity.ComplaintId;

public interface ComplaintRepository extends JpaRepository<Complaint, ComplaintId> {

    @Query(value = "SELECT c FROM Complaint c join c.department d where d.depId=?1",
        countQuery = "SELECT count(c) FROM Complaint c join c.department d where d.depId=?1")
    Page<Complaint> findAllByDepartmentId(Long depId, Pageable pageable);
}
