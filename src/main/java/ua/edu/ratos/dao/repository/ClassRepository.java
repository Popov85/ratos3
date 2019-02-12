package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.Class;

public interface ClassRepository extends JpaRepository<Class, Long> {

    //------------------------------------REGISTRATION dropdown-----------------------------

    @Query(value="select c from Class c join c.faculty f where f.facId = ?1 order by c.name asc")
    Slice<Class> findAllByFacultyId(Long facId, Pageable pageable);

}
