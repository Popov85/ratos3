package ua.edu.ratos.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Resource;
import ua.edu.ratos.dao.repository.ResourceRepository;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.ResourceInDto;
import ua.edu.ratos.service.dto.out.ResourceOutDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoResourceTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.ResourceDtoTransformer;

import javax.persistence.EntityNotFoundException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ResourceService {

    private static final String RESOURCE_NOT_FOUND = "The requested resource is not found, resId = ";

    private final ResourceRepository resourceRepository;

    private final DtoResourceTransformer dtoResourceTransformer;

    private final ResourceDtoTransformer resourceDtoTransformer;

    private final SecurityUtils securityUtils;


    //--------------------------------------------------CRUD------------------------------------------------------------
    @Transactional
    public ResourceOutDto save(@NonNull final ResourceInDto dto) {
        Resource resource = resourceRepository.save(this.dtoResourceTransformer.toEntity(dto));
        return resourceDtoTransformer.toDto(resource);
    }

    @Transactional
    public ResourceOutDto update(@NonNull final ResourceInDto dto) {
        if (dto.getResourceId()==null)
            throw new RuntimeException("Failed to update, resourceId is nullable!");
        Resource resource = resourceRepository.save(this.dtoResourceTransformer.toEntity(dto));
        return resourceDtoTransformer.toDto(resource);
    }

    @Transactional
    public void updateLink(@NonNull final Long resId, @NonNull final String link) {
        resourceRepository.findById(resId)
                .orElseThrow(() -> new EntityNotFoundException(RESOURCE_NOT_FOUND + resId))
                .setLink(link);
    }

    @Transactional
    public void updateDescription(@NonNull final Long resId, @NonNull final String description) {
        resourceRepository.findById(resId)
                .orElseThrow(() -> new EntityNotFoundException(RESOURCE_NOT_FOUND + resId))
                .setDescription(description);
    }

    @Transactional
    public void deleteByIdSoft(@NonNull final Long resId) {
        resourceRepository.findById(resId)
                .orElseThrow(() -> new EntityNotFoundException(RESOURCE_NOT_FOUND + resId))
                .setDeleted(true);
    }

    @Transactional
    public void deleteById(@NonNull final Long resId) {
        resourceRepository.deleteById(resId);
    }

    //------------------------------------------------Staff table-------------------------------------------------------

    @Transactional(readOnly = true)
    public Set<ResourceOutDto> findAllByDepartment() {
        return resourceRepository.findByDepartmentId(securityUtils.getAuthDepId()).stream().map(resourceDtoTransformer::toDto).collect(Collectors.toSet());
    }

    //--------------------------------------------For future references-------------------------------------------------
    @Transactional(readOnly = true)
    public Page<ResourceOutDto> findByStaffId(@NonNull final Pageable pageable) {
        return resourceRepository.findByStaffId(securityUtils.getAuthStaffId(), pageable).map(resourceDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ResourceOutDto> findByStaffIdAndDescriptionLettersContains(@NonNull final String letters, @NonNull final Pageable pageable) {
        return resourceRepository.findByStaffIdAndDescriptionLettersContains(securityUtils.getAuthStaffId(), letters, pageable).map(resourceDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ResourceOutDto> findByDepartmentId(@NonNull final Pageable pageable) {
        return resourceRepository.findByDepartmentId(securityUtils.getAuthDepId(), pageable).map(resourceDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ResourceOutDto> findByDepartmentIdAndDescriptionLettersContains(@NonNull final String letters, @NonNull final Pageable pageable) {
        return resourceRepository.findByDepartmentIdAndDescriptionLettersContains(securityUtils.getAuthDepId(), letters, pageable).map(resourceDtoTransformer::toDto);
    }

    //----------------------------------------------------ADMIN---------------------------------------------------------
    @Transactional(readOnly = true)
    public Page<ResourceOutDto> findAll(@NonNull final Pageable pageable) {
        return resourceRepository.findAll(pageable).map(resourceDtoTransformer::toDto);
    }
}
