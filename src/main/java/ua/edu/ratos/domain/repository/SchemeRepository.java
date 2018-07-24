package ua.edu.ratos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.domain.entity.Scheme;

public interface SchemeRepository extends JpaRepository<Scheme, Long> {
}
