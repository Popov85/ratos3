package ua.edu.ratos.service.dto.transformer;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.Phrase;
import ua.edu.ratos.domain.entity.PhraseResource;
import ua.edu.ratos.domain.entity.Resource;
import ua.edu.ratos.domain.entity.Staff;
import ua.edu.ratos.service.dto.entity.PhraseInDto;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Component
public class DtoPhraseTransformer {

    @Autowired
    private EntityManager em;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(propagation = Propagation.MANDATORY)
    public Phrase fromDto(@NonNull PhraseInDto dto) {
        return fromDto(dto, null);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Phrase fromDto(@NonNull PhraseInDto dto, Long phraseId) {
        Phrase phrase = modelMapper.map(dto, Phrase.class);
        phrase.setPhraseId(phraseId);
        phrase.setStaff(em.getReference(Staff.class, dto.getStaffId()));
        if (dto.getResourceId()!=null) {
            PhraseResource phraseResource = new PhraseResource();
            phraseResource.setPhrase(phrase);
            phraseResource.setResource(em.getReference(Resource.class, dto.getResourceId()));
            phrase.setPhraseResource(phraseResource);
        }
        phrase.setLastUsed(LocalDateTime.now());
        return phrase;
    }

}
