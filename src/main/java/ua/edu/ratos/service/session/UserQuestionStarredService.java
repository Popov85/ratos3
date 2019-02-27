package ua.edu.ratos.service.session;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.User;
import ua.edu.ratos.dao.entity.UserQuestionStarred;
import ua.edu.ratos.dao.entity.UserQuestionStarredId;
import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.dao.repository.UserQuestionStarredRepository;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.session.question.QuestionSessionMinOutDto;
import ua.edu.ratos.service.dto.session.question.QuestionSessionOutDto;
import ua.edu.ratos.service.transformer.entity_to_dto.UserQuestionStarredDtoTransformer;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

@Service
public class UserQuestionStarredService {

    public static final String ENTITY_NOT_FOUND = "Failed to find starred question by ID";

    @PersistenceContext
    private EntityManager em;

    private UserQuestionStarredRepository userQuestionStarredRepository;

    private UserQuestionStarredDtoTransformer userQuestionStarredDtoTransformer;

    private SecurityUtils securityUtils;

    @Autowired
    public void setUserQuestionStarredRepository(UserQuestionStarredRepository userQuestionStarredRepository) {
        this.userQuestionStarredRepository = userQuestionStarredRepository;
    }

    @Autowired
    public void setUserQuestionStarredDtoTransformer(UserQuestionStarredDtoTransformer userQuestionStarredDtoTransformer) {
        this.userQuestionStarredDtoTransformer = userQuestionStarredDtoTransformer;
    }

    @Autowired
    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    //--------------------------------------------------CRUD------------------------------------------------------------

    @Transactional
    public void save(@NonNull final Long questionId, final byte star) {
        Long userId = securityUtils.getAuthStudId();
        UserQuestionStarredId id = new UserQuestionStarredId(questionId, userId);
        UserQuestionStarred userQuestionStarred = new UserQuestionStarred();
        userQuestionStarred.setUserQuestionStarredId(id);
        userQuestionStarred.setUser(em.getReference(User.class, userId));
        userQuestionStarred.setQuestion(em.getReference(Question.class, questionId));
        userQuestionStarred.setStar(star);
        userQuestionStarredRepository.save(userQuestionStarred);
    }

    @Transactional
    public void updateStars(@NonNull final Long questionId, final byte star) {
        Long userId = securityUtils.getAuthStudId();
        UserQuestionStarredId id = new UserQuestionStarredId(questionId, userId);
        UserQuestionStarred userQuestionStarred = userQuestionStarredRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));
        userQuestionStarred.setStar(star);
    }

    @Transactional
    public void deleteById(@NonNull final Long questionId) {
        Long userId = securityUtils.getAuthStudId();
        userQuestionStarredRepository.deleteById(new UserQuestionStarredId(questionId, userId));
    }

    //--------------------------------------------One to see with answers-----------------------------------------------

    @Transactional(readOnly = true)
    // Full question with answers, no correct answer
    public QuestionSessionOutDto findOneByQuestionId(@NonNull final Long questionId) {
        Long userId = securityUtils.getAuthStudId();
        UserQuestionStarred userQuestionStarred = userQuestionStarredRepository.findById(new UserQuestionStarredId(userId, questionId))
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));
        return userQuestionStarredDtoTransformer.toDtoExt(userQuestionStarred);
    }

    //------------------------------------------------User short table--------------------------------------------------

    @Transactional(readOnly = true)
    // Only questions without answers
    public Page<QuestionSessionMinOutDto> findAllByUserId(@NonNull final Pageable pageable) {
        return userQuestionStarredRepository.findAllByUserId(securityUtils.getAuthStudId(), pageable).map(userQuestionStarredDtoTransformer::toDto);
    }

    // To check if limit is overflowed
    @Transactional(readOnly = true)
    public long countByUserId() {
        return userQuestionStarredRepository.countByUserId(securityUtils.getAuthStudId());
    }
}
