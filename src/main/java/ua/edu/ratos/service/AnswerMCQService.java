package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.Resource;
import ua.edu.ratos.domain.entity.answer.AnswerMultipleChoice;
import ua.edu.ratos.domain.repository.AnswerMCQRepository;
import ua.edu.ratos.domain.repository.QuestionMCQRepository;
import ua.edu.ratos.domain.repository.ResourceRepository;
import java.util.Optional;

@Service
public class AnswerMCQService {

    @Autowired
    private AnswerMCQRepository answerRepository;

    @Autowired
    private QuestionMCQRepository questionRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    /**
     * Saves only generic parameters of answer, in case any resources manipulation, refer to methods addResource(), deleteResource()
     * @param answer AnswerMultipleChoice to be persisted
     * @param questionId
     * @return generated Id
     */
    @Transactional
    public Long save(@NonNull AnswerMultipleChoice answer, @NonNull Long questionId) {
        answer.setQuestion(questionRepository.getOne(questionId));
        return answerRepository.save(answer).getAnswerId();
    }

    /**
     * Updates only generic fields of Answer
     * @param updatedAnswer
     */
    @Transactional
    public void update(@NonNull AnswerMultipleChoice updatedAnswer) {
        final Long answerId = updatedAnswer.getAnswerId();
        final Optional<AnswerMultipleChoice> optional = answerRepository.findById(answerId);
        final AnswerMultipleChoice oldAnswer = optional.orElseThrow(() ->
                new RuntimeException("AnswerMultipleChoice not found, ID = "+ answerId));
        oldAnswer.setAnswer(updatedAnswer.getAnswer());
        oldAnswer.setPercent(updatedAnswer.getPercent());
        oldAnswer.setRequired(updatedAnswer.isRequired());
    }

    @Transactional
    public void addResource(@NonNull Resource resource, @NonNull Long answerId) {
        final AnswerMultipleChoice answer = answerRepository.findByIdWithResources(answerId);
        answer.addResource(resource);
    }

    @Transactional
    public void addResource(@NonNull Long resourceId, @NonNull Long answerId) {
        final AnswerMultipleChoice answer = answerRepository.findByIdWithResources(answerId);
        final Resource resource = resourceRepository.getOne(resourceId);
        answer.addResource(resource);
    }

    @Transactional
    public void deleteResource(@NonNull Long resourceId, @NonNull Long answerId, boolean fromRepository) {
        final AnswerMultipleChoice answer = answerRepository.findByIdWithResources(answerId);
        final Resource deletableResource = resourceRepository.getOne(resourceId);
        answer.removeResource(deletableResource);
        if (fromRepository) resourceRepository.delete(deletableResource);
    }

    @Transactional
    public void deleteById(@NonNull Long answerId) {
        answerRepository.pseudoDeleteById(answerId);
    }
}
