package ua.edu.ratos.domain.repository;

import org.springframework.data.repository.CrudRepository;
import ua.edu.ratos.domain.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
