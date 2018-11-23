package ua.edu.ratos.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Resource;
import ua.edu.ratos.dao.repository.ResourceRepository;
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

    @Transactional
    public void update(@NonNull Long resId, @NonNull ResourceInDto dto) {
        if (!resourceRepository.existsById(resId))
            throw new RuntimeException("Failed to update phraseResource: ID does not exist");
        resourceRepository.save(transformer.fromDto(resId, dto));
    }

    @Transactional
    public void deleteById(@NonNull Long resourceId) {
        resourceRepository.deleteById(resourceId);
    }

    /*-----------------SELECT--------------------*/

    @Transactional(readOnly = true)
    public List<Resource> findByStaffId(@NonNull Long staffId) {
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

}
