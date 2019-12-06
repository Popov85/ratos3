package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "SELECT r FROM Role r where r.name = ?1")
    Optional<Role> findByName(String name);
}
