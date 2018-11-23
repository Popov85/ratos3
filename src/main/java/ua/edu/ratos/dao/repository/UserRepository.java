package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.dao.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
