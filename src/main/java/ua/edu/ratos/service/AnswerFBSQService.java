package ua.edu.ratos.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.repository.answer.AnswerFBSQRepository;
import ua.edu.ratos.service.dto.in.AnswerFBSQInDto;
import ua.edu.ratos.service.transformer.AnswerTransformer;

@Service
@AllArgsConstructor
public class AnswerFBSQService {

    private final AnswerFBSQRepository answerRepository;

    private final AnswerTransformer answerTransformer;


    @Transactional
    public void update(@NonNull final AnswerFBSQInDto dto) {
        if (dto.getAnswerId()==null)
            throw new RuntimeException("Answer FBSQ must have answerId to be updated!");
        answerRepository.save(answerTransformer.toEntity(dto));
    }
}
