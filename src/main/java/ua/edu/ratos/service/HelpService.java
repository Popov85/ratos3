package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.Help;
import ua.edu.ratos.domain.repository.HelpRepository;
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

    @Transactional
    public void update(@NonNull HelpInDto dto) {
        if (dto.getHelpId()==null || dto.getHelpId()==0)
            throw new RuntimeException("Invalid ID");
        Help help = transformer.fromDto(dto);
        helpRepository.save(help);
    }

    @Transactional
    public void deleteById(@NonNull Long helpId) {
        helpRepository.deleteById(helpId);
    }
}
