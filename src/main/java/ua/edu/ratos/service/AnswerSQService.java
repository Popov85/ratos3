package ua.edu.ratos.service;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.answer.AnswerSequence;
import ua.edu.ratos.domain.repository.AnswerSQRepository;
import ua.edu.ratos.domain.repository.QuestionSQRepository;
import ua.edu.ratos.domain.repository.ResourceRepository;
import ua.edu.ratos.service.dto.entity.AnswerSQInDto;

@Service
public class AnswerSQService {

    @Autowired
    private AnswerSQRepository answerRepository;

    @Autowired
    private QuestionSQRepository questionRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public Long save(@NonNull AnswerSQInDto dto) {
        return answerRepository.save(fromDto(dto)).getAnswerId();
    }

    @Transactional
    public void update(@NonNull AnswerSQInDto dto) {
        answerRepository.save(fromDto(dto));
    }

    @Transactional
    public void deletedById(@NonNull Long answerId) {
        answerRepository.pseudoDeleteById(answerId);
    }

    private AnswerSequence fromDto(AnswerSQInDto dto) {
        AnswerSequence answer = modelMapper.map(dto, AnswerSequence.class);
        if (dto.getResourceId()!=0) answer.addResource(resourceRepository.getOne(dto.getResourceId()));
        answer.setQuestion(questionRepository.getOne(dto.getQuestionId()));
        return answer;
    }

}
