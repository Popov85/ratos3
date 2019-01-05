package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.repository.AnswerFBSQRepository;
import ua.edu.ratos.service.dto.in.AnswerFBSQInDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoAnswerTransformer;

@Service
public class AnswerFBSQService {

    @Autowired
    private AnswerFBSQRepository answerRepository;

    @Autowired
    private DtoAnswerTransformer transformer;

    @Transactional
    public void update(@NonNull Long answerId, @NonNull AnswerFBSQInDto dto) {
        if (!answerRepository.existsById(answerId))
            throw new RuntimeException("Failed to update answer fbsq: ID does not exist");
        answerRepository.save(transformer.toEntity(answerId, dto));
    }
}
