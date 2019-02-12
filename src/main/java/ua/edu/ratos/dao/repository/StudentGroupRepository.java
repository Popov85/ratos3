package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.dao.entity.StudentGroup;
import ua.edu.ratos.dao.entity.StudentGroupId;

public interface StudentGroupRepository extends JpaRepository<StudentGroup, StudentGroupId> {
}
