package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.dao.entity.Access;

public interface AccessRepository extends JpaRepository<Access, Long> {
}
