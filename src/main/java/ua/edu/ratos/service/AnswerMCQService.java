package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.repository.AnswerMCQRepository;
import ua.edu.ratos.service.dto.in.AnswerMCQInDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoAnswerTransformer;

@Service
public class AnswerMCQService {

    @Autowired
    private AnswerMCQRepository answerRepository;

    @Autowired
    private DtoAnswerTransformer transformer;


    @Transactional
    public Long save(@NonNull AnswerMCQInDto dto) {
        return answerRepository.save(transformer.toEntity(dto)).getAnswerId();
    }

    @Transactional
    public void update(@NonNull Long answerId, @NonNull AnswerMCQInDto dto) {
        if (!answerRepository.existsById(answerId))
            throw new RuntimeException("Failed to update answer mcq: ID does not exist");
        answerRepository.save(transformer.toEntity(answerId, dto));
    }

    @Transactional
    public void deleteById(@NonNull Long answerId) {
        answerRepository.findById(answerId).get().setDeleted(true);
    }

}
