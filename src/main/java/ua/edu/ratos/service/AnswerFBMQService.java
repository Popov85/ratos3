package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.repository.AnswerFBMQRepository;
import ua.edu.ratos.service.dto.entity.AnswerFBMQInDto;
import ua.edu.ratos.service.dto.transformer.DtoAnswerTransformer;

@Service
public class AnswerFBMQService {

    @Autowired
    private AnswerFBMQRepository answerRepository;

    @Autowired
    private DtoAnswerTransformer transformer;

    @Transactional
    public Long save(@NonNull AnswerFBMQInDto dto) {
        return answerRepository.save(transformer.fromDto(dto)).getAnswerId();
    }

    @Transactional
    public void update(@NonNull Long answerId, @NonNull AnswerFBMQInDto dto) {
        if (!answerRepository.existsById(answerId))
            throw new RuntimeException("Failed to update answer fbmq: ID does not exist");
        answerRepository.save(transformer.fromDto(answerId, dto));
    }

    @Transactional
    public void deleteById(@NonNull Long answerId) {
        answerRepository.findById(answerId).get().setDeleted(true);
    }

}
