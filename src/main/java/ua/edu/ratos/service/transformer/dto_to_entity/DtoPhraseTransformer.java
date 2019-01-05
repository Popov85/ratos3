package ua.edu.ratos.service.transformer.dto_to_entity;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Phrase;
import ua.edu.ratos.dao.entity.Resource;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.service.dto.in.PhraseInDto;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Component
public class DtoPhraseTransformer {

    @Autowired
    private EntityManager em;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(propagation = Propagation.MANDATORY)
    public Phrase toEntity(@NonNull PhraseInDto dto) {
        return toEntity(dto, null);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Phrase toEntity(@NonNull PhraseInDto dto, Long phraseId) {
        Phrase phrase = modelMapper.map(dto, Phrase.class);
        phrase.setPhraseId(phraseId);
        phrase.setStaff(em.getReference(Staff.class, dto.getStaffId()));
        if (dto.getResourceId()!=null && !dto.getResourceId().equals(0)) {
            phrase.addResource(em.getReference(Resource.class, dto.getResourceId()));
        }
        phrase.setLastUsed(LocalDateTime.now());
        return phrase;
    }

}
