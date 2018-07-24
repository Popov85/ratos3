package ua.edu.ratos.domain.repository;

import org.springframework.data.repository.CrudRepository;
import ua.edu.ratos.domain.model.Session;

public interface SessionRepository extends CrudRepository<Session, String> {
}
