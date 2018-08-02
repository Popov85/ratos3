package ua.edu.ratos.service;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.Resource;
import ua.edu.ratos.domain.entity.answer.AnswerMultipleChoice;
import ua.edu.ratos.domain.repository.AnswerMCQRepository;
import ua.edu.ratos.domain.repository.QuestionMCQRepository;
import ua.edu.ratos.domain.repository.ResourceRepository;
import ua.edu.ratos.service.dto.entity.AnswerMCQInDto;


@Service
public class AnswerMCQService {

    @Autowired
    private AnswerMCQRepository answerRepository;

    @Autowired
    private QuestionMCQRepository questionRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public Long save(@NonNull AnswerMCQInDto dto) {
        return answerRepository.save(fromDto(dto)).getAnswerId();
    }

    @Transactional
    public void update(@NonNull AnswerMCQInDto dto) {
        answerRepository.save(fromDto(dto));
    }

    @Transactional
    public void addResource(@NonNull Long answerId, @NonNull Long resourceId) {
        final AnswerMultipleChoice answer = answerRepository.findByIdWithResources(answerId);
        // Will fetch it anyways
        final Resource resource = resourceRepository.getOne(resourceId);
        answer.addResource(resource);
    }

    @Transactional
    public void deleteResource(@NonNull Long answerId, @NonNull Long resourceId) {
        deleteResource(answerId, resourceId, false);
    }

    @Transactional
    public void deleteResource(@NonNull Long answerId, @NonNull Long resourceId, boolean fromRepository) {
        final AnswerMultipleChoice answer = answerRepository.findByIdWithResources(answerId);
        // Will fetch it anyways
        final Resource deletableResource = resourceRepository.getOne(resourceId);
        answer.removeResource(deletableResource);
        if (fromRepository) resourceRepository.delete(deletableResource);
    }

    @Transactional
    public void deleteById(@NonNull Long answerId) {
        answerRepository.pseudoDeleteById(answerId);
    }


    private AnswerMultipleChoice fromDto(AnswerMCQInDto dto) {
        AnswerMultipleChoice answer = modelMapper.map(dto, AnswerMultipleChoice.class);
        if (dto.getResourceId()!=0) answer.addResource(resourceRepository.getOne(dto.getResourceId()));
        answer.setQuestion(questionRepository.getOne(dto.getQuestionId()));
        return answer;
    }
}
