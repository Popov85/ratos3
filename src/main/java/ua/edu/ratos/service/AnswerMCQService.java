package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.repository.AnswerMCQRepository;
import ua.edu.ratos.service.dto.entity.AnswerMCQInDto;
import ua.edu.ratos.service.dto.transformer.DtoAnswerTransformer;

@Service
public class AnswerMCQService {

    @Autowired
    private AnswerMCQRepository answerRepository;

    @Autowired
    private DtoAnswerTransformer transformer;


    @Transactional
    public Long save(@NonNull AnswerMCQInDto dto) {
        return answerRepository.save(transformer.fromDto(dto)).getAnswerId();
    }

    @Transactional
    public void update(@NonNull Long answerId, @NonNull AnswerMCQInDto dto) {
        if (!answerRepository.existsById(answerId))
            throw new RuntimeException("Failed to update answer mcq: ID does not exist");
        answerRepository.save(transformer.fromDto(answerId, dto));
    }

    @Transactional
    public void deleteById(@NonNull Long answerId) {
        answerRepository.findById(answerId).get().setDeleted(true);
    }

}
