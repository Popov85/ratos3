package ua.edu.ratos.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.repository.AccessRepository;
import ua.edu.ratos.service.dto.out.AccessOutDto;
import ua.edu.ratos.service.transformer.AccessMapper;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccessService {

    private final AccessRepository accessRepository;

    private final AccessMapper accessMapper;

    @Transactional(readOnly = true)
    public List<AccessOutDto> findAllAccessesForDropDown() {
        return accessRepository.findAll()
                .stream()
                .map(accessMapper::toDto)
                .sorted(Comparator.comparing(AccessOutDto::getAccessId))
                .collect(Collectors.toList());
    }
}
