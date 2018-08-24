package ua.edu.ratos.service.dto.transformer;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.Staff;
import ua.edu.ratos.domain.entity.answer.AcceptedPhrase;
import ua.edu.ratos.service.dto.entity.AcceptedPhraseInDto;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Component
public class DtoAcceptedPhraseTransformer {

    @Autowired
    private EntityManager em;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(propagation = Propagation.MANDATORY)
    public AcceptedPhrase fromDto(@NonNull AcceptedPhraseInDto dto) {
        AcceptedPhrase phrase = modelMapper.map(dto, AcceptedPhrase.class);
        phrase.setStaff(em.getReference(Staff.class, dto.getStaffId()));
        phrase.setLastUsed(LocalDateTime.now());
        return phrase;
    }

}
