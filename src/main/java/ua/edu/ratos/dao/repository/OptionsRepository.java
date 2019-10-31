package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.Options;

import java.util.Optional;
import java.util.Set;

public interface OptionsRepository extends JpaRepository<Options, Long> {
    //------------------------------------------------------ONE for update----------------------------------------------
    @Query(value="select o from Options o join fetch o.staff st join fetch st.user where o.optId = ?1")
    Optional<Options> findOneForEdit(Long optId);

    //---------------------------------------------------------DEFAULT--------------------------------------------------
    @Query(value="select o from Options o join fetch o.staff st join fetch st.user where o.isDefault = true order by o.name asc")
    Set<Options> findAllDefault();

    //-------------------------------------------------------INSTRUCTOR table-------------------------------------------
    @Query(value="select o from Options o join fetch o.staff st join fetch st.user where st.staffId = ?1",
            countQuery = "select count(o) from Options o join o.staff st where st.staffId =?1")
    Page<Options> findAllByStaffId(Long staffId, Pageable pageable);

    @Query(value="select o from Options o join fetch o.staff st join fetch st.user join o.department d where d.depId = ?1",
            countQuery = "select count(o) from Options o join o.department d where d.depId =?1")
    Page<Options> findAllByDepartmentId(Long depId, Pageable pageable);

    //--------------------------------------------------------Table search----------------------------------------------
    @Query(value="select o from Options o join fetch o.staff st join fetch st.user where st.staffId = ?1 and o.name like %?2%",
            countQuery = "select count(o) from Options o join o.staff st where st.staffId =?1 and o.name like %?2%")
    Page<Options> findAllByStaffIdAndNameLettersContains(Long staffId, String contains, Pageable pageable);

    @Query(value="select o from Options o join fetch o.staff st join fetch st.user join o.department d where d.depId = ?1 and o.name like %?2%",
            countQuery = "select count(o) from Options o join o.department d where d.depId =?1 and o.name like %?2%")
    Page<Options> findAllByDepartmentIdAndNameLettersContains(Long depId, String contains, Pageable pageable);

    //------------------------------------------------------------ADMIN-------------------------------------------------
    @Query(value="select o from Options o join fetch o.staff st join fetch st.user join fetch o.department", countQuery = "select count(o) from Options o")
    Page<Options> findAllAdmin(Pageable pageable);
}
