package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.dao.entity.Result;

public interface ResultRepository extends JpaRepository<Result, Long> {
}
