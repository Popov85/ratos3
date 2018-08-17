package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.repository.AnswerFBSQRepository;
import ua.edu.ratos.service.dto.entity.AnswerFBSQInDto;
import ua.edu.ratos.service.dto.transformer.DtoAnswerTransformer;

@Service
public class AnswerFBSQService {

    @Autowired
    private AnswerFBSQRepository answerRepository;

    @Autowired
    private DtoAnswerTransformer transformer;

    @Transactional
    public void update(@NonNull AnswerFBSQInDto dto) {
        if (dto.getAnswerId()==null || dto.getAnswerId()==0)
            throw new RuntimeException("ID must be present for updates");
        answerRepository.save(transformer.fromDto(dto));
    }
}
