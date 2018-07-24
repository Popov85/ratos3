package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.Help;
import ua.edu.ratos.domain.entity.Resource;
import ua.edu.ratos.domain.repository.HelpRepository;
import ua.edu.ratos.domain.repository.QuestionRepository;
import ua.edu.ratos.domain.repository.ResourceRepository;
import ua.edu.ratos.domain.repository.StaffRepository;
import java.util.Optional;

@Service
public class HelpService {

    @Autowired
    private HelpRepository helpRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Transactional
    public Help save(@NonNull Help help, @NonNull Long questionId, @NonNull Long staffId) {
        help.setQuestion(questionRepository.getOne(questionId));
        help.setStaff(staffRepository.getOne(staffId));
        return helpRepository.save(help);
    }

    @Transactional
    public Help update(@NonNull Help updatedHelp) {
        final Long helpId = updatedHelp.getHelpId();
        final Optional<Help> optional = helpRepository.findById(helpId);
        final Help oldHelp = optional.orElseThrow(() ->
                new RuntimeException("Help not found, Id = "+ helpId));
        oldHelp.setName(updatedHelp.getName());
        oldHelp.setHelp(updatedHelp.getHelp());
        return oldHelp;
    }

    @Transactional
    public void addResource(@NonNull Resource resource, @NonNull Long helpId) {
        final Help help = helpRepository.findByIdWithResources(helpId);
        help.addResource(resource);
    }

    @Transactional
    public void addResource(@NonNull Long resourceId, @NonNull Long helpId) {
        final Help help = helpRepository.findByIdWithResources(helpId);
        final Resource resource = resourceRepository.getOne(resourceId);
        help.addResource(resource);
    }

    @Transactional
    public void deleteResource(@NonNull Long resourceId, @NonNull Long helpId, boolean fromRepository) {
        final Help help = helpRepository.findByIdWithResources(helpId);
        final Resource deletableResource = resourceRepository.getOne(resourceId);
        help.removeResource(deletableResource);
        if (fromRepository) resourceRepository.delete(deletableResource);
    }

    @Transactional
    public void deleteById(@NonNull Long helpId) {
        helpRepository.deleteById(helpId);
    }
}
