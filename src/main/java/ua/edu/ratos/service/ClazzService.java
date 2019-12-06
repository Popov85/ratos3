package ua.edu.ratos.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.repository.ClazzRepository;
import ua.edu.ratos.service.dto.out.ClassMinOutDto;
import ua.edu.ratos.service.transformer.entity_to_dto.ClassMinDtoTransformer;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClazzService {

    private final ClazzRepository classRepository;

    private final ClassMinDtoTransformer classMinDtoTransformer;

    // TODO: CRUD by dep. staff;

    /**
     * Up to 100-200 classes are expected per single faculty
     * @param facId facId
     * @return set of Class DTOs
     */
    @Transactional(readOnly = true)
    public Set<ClassMinOutDto> findAllClassesByFacIdForDropDown(@NonNull final Long facId) {
        return classRepository
                .findAllByFacultyId(facId)
                .stream()
                .map(classMinDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }
}
