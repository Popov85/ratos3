package ua.edu.ratos.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.repository.AccessRepository;
import ua.edu.ratos.service.dto.out.AccessOutDto;
import ua.edu.ratos.service.transformer.entity_to_dto.AccessDtoTransformer;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccessService {

    private final AccessRepository accessRepository;

    private final AccessDtoTransformer accessDtoTransformer;


    @Transactional(readOnly = true)
    public Set<AccessOutDto> findAllAccessesForDropDown() {
        return accessRepository.findAll()
                .stream()
                .map(accessDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }
}
