package ua.edu.ratos.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.repository.answer.AnswerFBSQRepository;
import ua.edu.ratos.service.dto.in.AnswerFBSQInDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoAnswerTransformer;

@Service
@AllArgsConstructor
public class AnswerFBSQService {

    private final AnswerFBSQRepository answerRepository;

    private final DtoAnswerTransformer dtoAnswerTransformer;


    @Transactional
    public void update(@NonNull final AnswerFBSQInDto dto) {
        if (dto.getAnswerId()==null) throw new RuntimeException("Answer FBSQ must have answerId to be updated!");
        answerRepository.save(dtoAnswerTransformer.toEntity(dto));
    }
}
