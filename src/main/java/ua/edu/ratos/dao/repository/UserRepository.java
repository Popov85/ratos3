package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT u FROM User u where u.email = ?1")
    User findByEmail(String email);
}
