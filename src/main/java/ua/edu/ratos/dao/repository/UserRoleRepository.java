package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.dao.entity.UserRole;
import ua.edu.ratos.dao.entity.UserRoleId;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
}
