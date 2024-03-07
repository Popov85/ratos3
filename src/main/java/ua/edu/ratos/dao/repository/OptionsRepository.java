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
    @Query(value="select o from Options o join fetch o.staff st join fetch st.user where o.isDefault = true")
    Set<Options> findAllDefault();

    //--------------------------------------------------INSTRUCTOR table/drop-down--------------------------------------
    @Query(value="select o from Options o join fetch o.staff st join fetch st.user join o.department d where o.isDefault = false and d.depId = ?1")
    Set<Options> findAllByDepartmentId(Long depId);

    //------------------------------------------------------------ADMIN-------------------------------------------------
    @Query(value="select o from Options o join fetch o.staff st join fetch st.user join fetch o.department",
            countQuery = "select count(o) from Options o")
    Page<Options> findAllAdmin(Pageable pageable);
}
