package ua.edu.ratos.dao.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.dao.entity.PasswordReset;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, String> {
}
