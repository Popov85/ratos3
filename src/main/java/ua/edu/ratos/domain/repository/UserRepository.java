package ua.edu.ratos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
