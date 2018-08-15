package ua.edu.ratos.service;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.answer.AnswerMatcher;
import ua.edu.ratos.domain.repository.AnswerMQRepository;
import ua.edu.ratos.domain.repository.QuestionMQRepository;
import ua.edu.ratos.domain.repository.ResourceRepository;
import ua.edu.ratos.service.dto.entity.AnswerMQInDto;

@Service
public class AnswerMQService {

    @Autowired
    private AnswerMQRepository answerRepository;
    @Autowired
    private QuestionMQRepository questionRepository;
    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public Long save(@NonNull AnswerMQInDto dto) {
        return answerRepository.save(fromDto(dto)).getAnswerId();
    }

    @Transactional
    public void update(@NonNull AnswerMQInDto dto) {
        answerRepository.save(fromDto(dto));
    }

    @Transactional
    public void deletedById(@NonNull Long answerId) {
        answerRepository.pseudoDeleteById(answerId);
    }

    private AnswerMatcher fromDto(AnswerMQInDto dto) {
        AnswerMatcher answer = modelMapper.map(dto, AnswerMatcher.class);
        if (dto.getRightPhraseResourceId()!=0) answer.addResource(resourceRepository.getOne(dto.getRightPhraseResourceId()));
        answer.setQuestion(questionRepository.getOne(dto.getQuestionId()));
        return answer;
    }
}
