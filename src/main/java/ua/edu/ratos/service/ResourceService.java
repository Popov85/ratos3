package ua.edu.ratos.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.Resource;
import ua.edu.ratos.domain.repository.ResourceRepository;
import ua.edu.ratos.service.dto.entity.ResourceInDto;
import ua.edu.ratos.service.dto.transformer.DtoResourceTransformer;
import java.util.List;

@Slf4j
@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private DtoResourceTransformer transformer;

    @Autowired
    private PropertiesService propertiesService;

    @Transactional
    public Long save(@NonNull ResourceInDto dto) {
        return resourceRepository.save(transformer.fromDto(dto)).getResourceId();
    }

    @Transactional(readOnly = true)
    public List<Resource> findByStaffId(@NonNull Long staffId) {
        log.debug("size = {}", propertiesService.getInitCollectionSize());
        return resourceRepository.findByStaffId(staffId, propertiesService.getInitCollectionSize()).getContent();
    }

    @Transactional(readOnly = true)
    public List<Resource> findByStaffIdAndFirstLetters(@NonNull Long staffId, @NonNull String starts) {
        return resourceRepository.findByStaffIdAndFirstLetters(staffId, starts, propertiesService.getInitCollectionSize()).getContent();
    }

    @Transactional(readOnly = true)
    public List<Resource> findByDepartmentId(@NonNull Long depId) {
        return resourceRepository.findByDepartmentId(depId, propertiesService.getInitCollectionSize()).getContent();
    }

    @Transactional(readOnly = true)
    public List<Resource> findByDepartmentIdAndFirstLetters(@NonNull Long staffId, @NonNull String starts) {
        return resourceRepository.findByDepartmentIdAndFirstLetters(staffId, starts, propertiesService.getInitCollectionSize()).getContent();
    }

    @Transactional
    public void update(@NonNull ResourceInDto dto) {
        if (dto.getResourceId()==null || dto.getResourceId()==0)
            throw new RuntimeException("Invalid ID");
        resourceRepository.save(transformer.fromDto(dto));
    }

    @Transactional
    public void deleteById(@NonNull Long resourceId) {
        resourceRepository.deleteById(resourceId);
    }

}
