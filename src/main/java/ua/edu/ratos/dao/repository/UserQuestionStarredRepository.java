package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.UserQuestionStarred;
import ua.edu.ratos.dao.entity.UserQuestionStarredId;

public interface UserQuestionStarredRepository extends JpaRepository<UserQuestionStarred, UserQuestionStarredId> {

    @Query(value = "SELECT s FROM UserQuestionStarred s join s.user u join fetch s.question q where u.userId =?1",
            countQuery = "SELECT count(s) FROM UserQuestionStarred s join s.user u where u.userId=?1")
    Page<UserQuestionStarred> findAllByUserId(Long userId, Pageable pageable);

    @Query(value = "SELECT count(s) FROM UserQuestionStarred s join s.user u where u.userId=?1")
    long countByUserId(Long userId);

}
