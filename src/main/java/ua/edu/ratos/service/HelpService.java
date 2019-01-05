package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Help;
import ua.edu.ratos.dao.repository.HelpRepository;
import ua.edu.ratos.service.dto.in.HelpInDto;
import ua.edu.ratos.service.dto.out.HelpOutDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoHelpTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.HelpDtoTransformer;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HelpService {

    @Autowired
    private HelpRepository helpRepository;

    @Autowired
    private DtoHelpTransformer dtoHelpTransformer;

    @Autowired
    private PropertiesService propertiesService;

    @Autowired
    private HelpDtoTransformer helpDtoTransformer;

    @Transactional
    public Long save(@NonNull final HelpInDto dto) {
        Help help = dtoHelpTransformer.toEntity(dto);
        return helpRepository.save(help).getHelpId();
    }

    @Transactional
    public void update(@NonNull final Long helpId, @NonNull final HelpInDto dto) {
        if (!helpRepository.existsById(helpId))
            throw new RuntimeException("Failed to update help: ID does not exist");
        helpRepository.save(dtoHelpTransformer.toEntity(helpId, dto));
    }

    @Transactional
    public void deleteById(@NonNull Long helpId) {
        helpRepository.deleteById(helpId);
    }


    //--------------------SELECT--------------------

    @Transactional(readOnly = true)
    public Page<HelpOutDto> findAll(@NonNull Pageable pageable) {
        Page<Help> page = helpRepository.findAll(pageable);
        return new PageImpl<>(toDto(page.getContent()), pageable, page.getTotalElements());
    }

    @Transactional(readOnly = true)
    public List<HelpOutDto> findByStaffIdWithResources(@NonNull Long staffId) {
        List<Help> content = helpRepository.findByStaffIdWithResources(staffId, propertiesService.getInitCollectionSize()).getContent();
        return toDto(content);
    }

    @Transactional(readOnly = true)
    public List<HelpOutDto> findByStaffIdAndFirstNameLettersWithResources(@NonNull Long staffId, @NonNull String starts) {
        List<Help> content = helpRepository.findByStaffIdAndFirstNameLettersWithResources(staffId, starts, propertiesService.getInitCollectionSize()).getContent();
        return toDto(content);
    }

    @Transactional(readOnly = true)
    public List<HelpOutDto> findByDepartmentIdWithResources(@NonNull Long depId) {
        List<Help> content = helpRepository.findByDepartmentIdWithResources(depId, propertiesService.getInitCollectionSize()).getContent();
        return toDto(content);
    }

    @Transactional(readOnly = true)
    public List<HelpOutDto> findByDepartmentIdAndFirstNameLettersWithResources(@NonNull Long depId, @NonNull String starts) {
        List<Help> content = helpRepository.findByDepartmentIdAndFirstNameLettersWithResources(depId, starts, propertiesService.getInitCollectionSize()).getContent();
        return toDto(content);
    }

    private List<HelpOutDto> toDto(@NonNull final List<Help> content) {
        return content.stream().map(helpDtoTransformer::toDto).collect(Collectors.toList());
    }

}
