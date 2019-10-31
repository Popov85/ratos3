package ua.edu.ratos.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.repository.answer.AnswerMCQRepository;
import ua.edu.ratos.service.dto.in.AnswerMCQInDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoAnswerTransformer;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class AnswerMCQService {

    private static final String ANSWER_NOT_FOUND = "The requested Answer not found, answerId = ";

    private final AnswerMCQRepository answerRepository;

    private final DtoAnswerTransformer dtoAnswerTransformer;


    @Transactional
    public Long save(@NonNull final Long questionId, @NonNull final AnswerMCQInDto dto) {
        return answerRepository.save(dtoAnswerTransformer.toEntity(questionId, dto)).getAnswerId();
    }

    @Transactional
    public void update(@NonNull final Long questionId, @NonNull final AnswerMCQInDto dto) {
        if (dto.getAnswerId()==null) throw new RuntimeException("Answer MCQ must have answerId to be updated");
        answerRepository.save(dtoAnswerTransformer.toEntity(questionId, dto));
    }

    @Transactional
    public void deleteById(@NonNull final Long answerId) {
        answerRepository.findById(answerId).orElseThrow(()->new EntityNotFoundException(ANSWER_NOT_FOUND + answerId))
                .setDeleted(true);
    }

}
