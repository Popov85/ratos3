package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.entity.SessionPreserved;
import ua.edu.ratos.dao.entity.User;
import ua.edu.ratos.dao.repository.SessionPreservedRepository;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.dto.out.SessionPreservedOutDto;
import ua.edu.ratos.service.session.SessionDataSerializerService;
import ua.edu.ratos.service.transformer.entity_to_dto.SessionPreservedDtoTransformer;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@Service
public class SessionPreservedService {

    private static final String ENTITY_NOT_FOUND = "The requested preserved session not found, key = ";
    private static final String CORRUPT_RETRIEVAL_ACCESS = "Requested session was created by a different user";


    @PersistenceContext
    private EntityManager em;

    private SessionPreservedRepository sessionPreservedRepository;

    private SessionPreservedDtoTransformer sessionPreservedDtoTransformer;

    private SessionDataSerializerService serializer;

    private SecurityUtils securityUtils;

    @Autowired
    public void setSessionPreservedRepository(SessionPreservedRepository sessionPreservedRepository) {
        this.sessionPreservedRepository = sessionPreservedRepository;
    }

    @Autowired
    public void setSerializer(SessionDataSerializerService serializer) {
        this.serializer = serializer;
    }

    @Autowired
    public void setSessionPreservedDtoTransformer(SessionPreservedDtoTransformer sessionPreservedDtoTransformer) {
        this.sessionPreservedDtoTransformer = sessionPreservedDtoTransformer;
    }

    @Autowired
    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }


    //------------------------------------------------------CRUD--------------------------------------------------------

    @Transactional
    public String save(@NonNull final SessionData sessionData) {
        SessionPreserved sessionPreserved = new SessionPreserved();
        sessionPreserved.setKey(sessionData.getKey());
        sessionPreserved.setData(serializer.serialize(sessionData));
        sessionPreserved.setScheme(em.getReference(Scheme.class, sessionData.getSchemeDomain().getSchemeId()));
        sessionPreserved.setUser(em.getReference(User.class, securityUtils.getAuthUserId()));
        sessionPreserved.setWhenPreserved(LocalDateTime.now());
        sessionPreserved.setProgress(sessionData.getProgressData().getProgress());
        sessionPreservedRepository.save(sessionPreserved);
        return sessionPreserved.getKey();
    }

    @Transactional
    public SessionData retrieve(@NonNull final String key) {
        SessionPreserved sessionPreserved = findOneById(key);
        SessionData sessionData = serializer.deserialize(sessionPreserved.getData());
        sessionPreservedRepository.delete(sessionPreserved);
        return sessionData;
    }

    @Transactional
    public void deleteById(@NonNull final String key) {
        // Protect from deleting preserved sessions not belonging to you
        SessionPreserved sessionPreserved = findOneById(key);
        sessionPreservedRepository.delete(sessionPreserved);
    }

    @Transactional(readOnly = true)
    public SessionPreserved findOneById(@NonNull final String key) {
        SessionPreserved sessionPreserved = sessionPreservedRepository.findById(key)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND + key));
        Long currentStudentId = securityUtils.getAuthUserId();
        Long preservedUserId = sessionPreserved.getUser().getUserId();
        if (!preservedUserId.equals(currentStudentId)) throw new AccessDeniedException(CORRUPT_RETRIEVAL_ACCESS);
        return sessionPreserved;
    }

    //----------------------------------------------------User table----------------------------------------------------

    @Transactional(readOnly = true)
    public Slice<SessionPreservedOutDto> findAllByUserId(@NonNull final Pageable pageable) {
       return sessionPreservedRepository.findAllByUserId(securityUtils.getAuthUserId(), pageable).map(sessionPreservedDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    // There is a limit for how many preservation-s a user can do, 5 by default
    public long countByUserId(@NonNull final Long userId) {
        return sessionPreservedRepository.countByUserId(userId);
    }
}
