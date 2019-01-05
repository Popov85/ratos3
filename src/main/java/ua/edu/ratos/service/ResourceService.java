package ua.edu.ratos.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Resource;
import ua.edu.ratos.dao.repository.ResourceRepository;
import ua.edu.ratos.service.dto.in.ResourceInDto;
import ua.edu.ratos.service.dto.out.ResourceOutDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoResourceTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.ResourceDtoTransformer;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private DtoResourceTransformer dtoResourceTransformer;

    @Autowired
    private PropertiesService propertiesService;

    @Autowired
    private ResourceDtoTransformer resourceDtoTransformer;

    @Transactional
    public Long save(@NonNull ResourceInDto dto) {
        return resourceRepository.save(this.dtoResourceTransformer.toEntity(dto)).getResourceId();
    }

    @Transactional
    public void update(@NonNull Long resId, @NonNull ResourceInDto dto) {
        if (!resourceRepository.existsById(resId))
            throw new RuntimeException("Failed to update the Resource: ID does not exist");
        resourceRepository.save(this.dtoResourceTransformer.toEntity(resId, dto));
    }

    @Transactional
    public void deleteById(@NonNull Long resourceId) {
        resourceRepository.deleteById(resourceId);
    }


    /*-----------------SELECT--------------------*/


    @Transactional(readOnly = true)
    public Page<ResourceOutDto> findAll(@NonNull Pageable pageable) {
        Page<Resource> page = resourceRepository.findAll(pageable);
        return new PageImpl<>(toDto(page.getContent()), pageable, page.getTotalElements());
    }

    @Transactional(readOnly = true)
    public List<ResourceOutDto> findByStaffId(@NonNull Long staffId) {
        List<Resource> content = resourceRepository.findByStaffId(staffId, propertiesService.getInitCollectionSize()).getContent();
        return toDto(content);
    }

    @Transactional(readOnly = true)
    public List<ResourceOutDto> findByStaffIdAndFirstLetters(@NonNull Long staffId, @NonNull String starts) {
        List<Resource> content = resourceRepository.findByStaffIdAndFirstLetters(staffId, starts, propertiesService.getInitCollectionSize()).getContent();
        return toDto(content);
    }

    @Transactional(readOnly = true)
    public List<ResourceOutDto> findByDepartmentId(@NonNull Long depId) {
        List<Resource> content = resourceRepository.findByDepartmentId(depId, propertiesService.getInitCollectionSize()).getContent();
        return toDto(content);
    }

    @Transactional(readOnly = true)
    public List<ResourceOutDto> findByDepartmentIdAndFirstLetters(@NonNull Long staffId, @NonNull String starts) {
        List<Resource> content = resourceRepository.findByDepartmentIdAndFirstLetters(staffId, starts, propertiesService.getInitCollectionSize()).getContent();
        return toDto(content);
    }

    private List<ResourceOutDto> toDto(@NonNull final List<Resource> content) {
        return content.stream().map(resourceDtoTransformer::toDto).collect(Collectors.toList());
    }
}
