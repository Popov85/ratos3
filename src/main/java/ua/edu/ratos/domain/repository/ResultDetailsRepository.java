package ua.edu.ratos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.domain.entity.ResultDetails;

public interface ResultDetailsRepository extends JpaRepository<ResultDetails, Long> {
}
