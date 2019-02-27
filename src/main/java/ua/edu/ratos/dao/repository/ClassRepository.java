package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.Class;

public interface ClassRepository extends JpaRepository<Class, Long> {

    //--------------------------------------------REGISTRATION dropdown-------------------------------------------------

    @Query(value="select c from Class c join c.faculty f where f.facId = ?1")
    Slice<Class> findAllByFacultyId(Long facId, Pageable pageable);

    @Query(value="select c from Class c join c.faculty f where f.facId = ?1 and c.name like ?2%")
    Slice<Class> findAllByFacultyIdAndNameStarts(Long facId, String starts, Pageable pageable);

    @Query(value="select c from Class c join c.faculty f where f.facId = ?1 and c.name like %?2%")
    Slice<Class> findAllByFacultyIdAndNameLettersContains(Long facId, String contains, Pageable pageable);

}
