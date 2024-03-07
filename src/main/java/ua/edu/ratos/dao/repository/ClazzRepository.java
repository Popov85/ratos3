package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.Clazz;

import java.util.Set;

public interface ClazzRepository extends JpaRepository<Clazz, Long> {

    //-------------------------------------------------For DropDown-----------------------------------------------------

    @Query(value="select new Clazz(c.classId, c.name) from Clazz c join c.faculty f where f.facId = ?1")
    Set<Clazz> findAllByFacultyId(Long facId);


    //-----------------------------------------------Just for any case--------------------------------------------------

    @Query(value="select c from Clazz c join c.faculty f where f.facId = ?1")
    Slice<Clazz> findAllByFacultyId(Long facId, Pageable pageable);

    @Query(value="select c from Clazz c join c.faculty f where f.facId = ?1 and c.name like ?2%")
    Slice<Clazz> findAllByFacultyIdAndNameStarts(Long facId, String starts, Pageable pageable);

    @Query(value="select c from Clazz c join c.faculty f where f.facId = ?1 and c.name like %?2%")
    Slice<Clazz> findAllByFacultyIdAndNameLettersContains(Long facId, String contains, Pageable pageable);

}
