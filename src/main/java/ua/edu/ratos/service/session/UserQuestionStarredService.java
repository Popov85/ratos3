package ua.edu.ratos.service.session;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.User;
import ua.edu.ratos.dao.entity.UserQuestionStarred;
import ua.edu.ratos.dao.entity.UserQuestionStarredId;
import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.dao.repository.UserQuestionStarredRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class UserQuestionStarredService {

    @PersistenceContext
    private EntityManager em;

    private UserQuestionStarredRepository userQuestionStarredRepository;

    @Autowired
    public void setUserQuestionStarredRepository(UserQuestionStarredRepository userQuestionStarredRepository) {
        this.userQuestionStarredRepository = userQuestionStarredRepository;
    }

    public void save(@NonNull final Long userId, @NonNull final Long questionId, final byte star) {
        UserQuestionStarredId id = new UserQuestionStarredId();
        id.setQuestionId(questionId);
        id.setUserId(userId);
        UserQuestionStarred userQuestionStarred = new UserQuestionStarred();
        userQuestionStarred.setUserQuestionStarredId(id);
        userQuestionStarred.setUser(em.getReference(User.class, userId));
        userQuestionStarred.setQuestion(em.getReference(Question.class, questionId));
        userQuestionStarred.setStar(star);
        userQuestionStarredRepository.save(userQuestionStarred);
    }
}
