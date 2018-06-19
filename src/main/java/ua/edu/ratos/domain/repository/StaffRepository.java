package ua.edu.ratos.domain.repository;

import org.springframework.data.repository.CrudRepository;
import ua.edu.ratos.domain.entity.Staff;

public interface StaffRepository extends CrudRepository<Staff, Long> {
}
