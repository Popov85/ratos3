package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Help;
import ua.edu.ratos.dao.repository.HelpRepository;
import ua.edu.ratos.service.dto.entity.HelpInDto;
import ua.edu.ratos.service.dto.transformer.DtoHelpTransformer;
import java.util.List;

@Service
public class HelpService {

    @Autowired
    private HelpRepository helpRepository;

    @Autowired
    private DtoHelpTransformer transformer;

    @Autowired
    private PropertiesService propertiesService;

    @Transactional
    public Long save(@NonNull HelpInDto dto) {
        Help help = transformer.fromDto(dto);
        return helpRepository.save(help).getHelpId();
    }

    @Transactional
    public void update(@NonNull Long helpId, @NonNull HelpInDto dto) {
        if (!helpRepository.existsById(helpId))
            throw new RuntimeException("Failed to update helpAvailable: ID does not exist");
        helpRepository.save(transformer.fromDto(helpId, dto));
    }

    @Transactional
    public void deleteById(@NonNull Long helpId) {
        helpRepository.deleteById(helpId);
    }

    /*--------------------SELECT--------------------*/

    @Transactional(readOnly = true)
    public List<Help> findByStaffIdWithResources(@NonNull Long staffId) {
        return helpRepository.findByStaffIdWithResources(staffId, propertiesService.getInitCollectionSize()).getContent();
    }

    @Transactional(readOnly = true)
    public List<Help> findByStaffIdAndFirstNameLettersWithResources(@NonNull Long staffId, @NonNull String starts) {
        return helpRepository.findByStaffIdAndFirstNameLettersWithResources(staffId, starts, propertiesService.getInitCollectionSize()).getContent();
    }

    @Transactional(readOnly = true)
    public List<Help> findByDepartmentIdWithResources(@NonNull Long depId) {
        return helpRepository.findByDepartmentIdWithResources(depId, propertiesService.getInitCollectionSize()).getContent();
    }

    @Transactional(readOnly = true)
    public List<Help> findByDepartmentIdAndFirstNameLettersWithResources(@NonNull Long depId, @NonNull String starts) {
        return helpRepository.findByDepartmentIdAndFirstNameLettersWithResources(depId, starts, propertiesService.getInitCollectionSize()).getContent();
    }
}
