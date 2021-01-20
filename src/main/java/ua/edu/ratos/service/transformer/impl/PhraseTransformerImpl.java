package ua.edu.ratos.service.transformer.impl;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Phrase;
import ua.edu.ratos.dao.entity.Resource;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.PhraseInDto;
import ua.edu.ratos.service.transformer.PhraseTransformer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class PhraseTransformerImpl implements PhraseTransformer {

    @PersistenceContext
    private final EntityManager em;

    private final SecurityUtils securityUtils;

    public Phrase toEntity(@NonNull final PhraseInDto dto) {
        Phrase phrase = new Phrase();
        phrase.setPhrase(dto.getPhrase());
        phrase.setStaff(em.getReference(Staff.class, securityUtils.getAuthStaffId()));
        if (dto.getResourceId()!=null && !dto.getResourceId().equals(0)) {
            phrase.addResource(em.getReference(Resource.class, dto.getResourceId()));
        }
        phrase.setLastUsed(LocalDateTime.now());
        return phrase;
    }
}
