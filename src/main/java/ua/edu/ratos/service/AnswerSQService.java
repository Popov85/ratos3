package ua.edu.ratos.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.repository.answer.AnswerSQRepository;
import ua.edu.ratos.service.dto.in.AnswerSQInDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoAnswerTransformer;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class AnswerSQService {

    private static final String ANSWER_NOT_FOUND = "The requested Answer SQ not found, answerId = ";

    private final AnswerSQRepository answerRepository;

    private final DtoAnswerTransformer dtoAnswerTransformer;

    @Transactional
    public Long save(@NonNull final Long questionId, @NonNull final AnswerSQInDto dto) {
        return answerRepository.save(dtoAnswerTransformer.toEntity(questionId, dto)).getAnswerId();
    }

    @Transactional
    public void update(@NonNull final Long questionId, @NonNull final AnswerSQInDto dto) {
        if (dto.getAnswerId()==null) throw new RuntimeException();
        answerRepository.save(dtoAnswerTransformer.toEntity(questionId, dto));
    }

    @Transactional
    public void deleteById(@NonNull final Long answerId) {
        answerRepository.findById(answerId).orElseThrow(()->new EntityNotFoundException(ANSWER_NOT_FOUND + answerId))
                .setDeleted(true);
    }
}
