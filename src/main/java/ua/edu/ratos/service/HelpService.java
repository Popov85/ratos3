package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Help;
import ua.edu.ratos.dao.entity.Resource;
import ua.edu.ratos.dao.repository.HelpRepository;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.HelpInDto;
import ua.edu.ratos.service.dto.out.HelpOutDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoHelpTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.HelpDtoTransformer;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

@Service
public class HelpService {

    private static final String HELP_NOT_FOUND = "The requested Help not found, helpId = ";

    @PersistenceContext
    private EntityManager em;

    private HelpRepository helpRepository;

    private DtoHelpTransformer dtoHelpTransformer;

    private HelpDtoTransformer helpDtoTransformer;

    private SecurityUtils securityUtils;

    @Autowired
    public void setHelpRepository(HelpRepository helpRepository) {
        this.helpRepository = helpRepository;
    }

    @Autowired
    public void setDtoHelpTransformer(DtoHelpTransformer dtoHelpTransformer) {
        this.dtoHelpTransformer = dtoHelpTransformer;
    }

    @Autowired
    public void setHelpDtoTransformer(HelpDtoTransformer helpDtoTransformer) {
        this.helpDtoTransformer = helpDtoTransformer;
    }

    @Autowired
    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    @Transactional
    public Long save(@NonNull final HelpInDto dto) {
        Help help = dtoHelpTransformer.toEntity(dto);
        return helpRepository.save(help).getHelpId();
    }

    @Transactional
    public void updateName(@NonNull final Long helpId, @NonNull final String name) {
        helpRepository.findById(helpId).orElseThrow(() -> new EntityNotFoundException(HELP_NOT_FOUND + helpId))
                .setName(name);
    }

    @Transactional
    public void updateHelp(@NonNull final Long helpId, @NonNull final String help) {
        helpRepository.findById(helpId).orElseThrow(() -> new EntityNotFoundException(HELP_NOT_FOUND + helpId))
                .setHelp(help);
    }

    @Transactional
    public void updateResource(@NonNull final Long helpId, @NonNull final Long resId) {
        Help help = helpRepository.findById(helpId).orElseThrow(() -> new EntityNotFoundException(HELP_NOT_FOUND + helpId));
        help.clearResources();
        help.addResource(em.getReference(Resource.class, resId));
    }


    @Transactional
    public void deleteById(@NonNull final Long helpId) {
        helpRepository.deleteById(helpId);
    }

    //-------------------------------------------ONE for update--------------------------------------

    @Transactional(readOnly = true)
    public HelpOutDto findOneForUpdates(@NonNull final Long helpId) {
        return helpDtoTransformer.toDto(helpRepository.findOneForUpdate(helpId));
    }

    //------------------------------------------INSTRUCTOR table-------------------------------------

    @Transactional(readOnly = true)
    public Page<HelpOutDto> findAllByStaffId(@NonNull final Pageable pageable) {
        return helpRepository.findAllByStaffId(securityUtils.getAuthStaffId(), pageable).map(helpDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<HelpOutDto> findAllByStaffIdAndNameLettersContains(@NonNull final String letters, @NonNull final Pageable pageable) {
        return helpRepository.findAllByStaffIdAndNameLettersContains(securityUtils.getAuthStaffId(), letters, pageable).map(helpDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<HelpOutDto> findAllByDepartmentId(@NonNull final Pageable pageable) {
        return helpRepository.findAllByDepartmentId(securityUtils.getAuthDepId(), pageable).map(helpDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<HelpOutDto> findAllByDepartmentIdAndNameLettersContains(@NonNull final String letters, @NonNull final Pageable pageable) {
        return helpRepository.findAllByDepartmentIdAndNameLettersContains(securityUtils.getAuthDepId(), letters, pageable).map(helpDtoTransformer::toDto);
    }

    //---------------------------------------------ADMIN---------------------------------------------
    @Transactional(readOnly = true)
    public Page<HelpOutDto> findAll(@NonNull final Pageable pageable) {
        return helpRepository.findAll(pageable).map(helpDtoTransformer::toDto);
    }

}
