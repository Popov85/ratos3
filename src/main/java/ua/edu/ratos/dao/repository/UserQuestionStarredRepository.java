package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.dao.entity.UserQuestionStarred;
import ua.edu.ratos.dao.entity.UserQuestionStarredId;

public interface UserQuestionStarredRepository extends JpaRepository<UserQuestionStarred, UserQuestionStarredId> {
}
