package ua.edu.ratos.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.repository.StrategyRepository;
import ua.edu.ratos.service.dto.out.StrategyOutDto;
import ua.edu.ratos.service.transformer.entity_to_dto.StrategyDtoTransformer;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StrategyService {

    private final StrategyRepository strategyRepository;

    private final StrategyDtoTransformer strategyDtoTransformer;


    @Transactional(readOnly = true)
    public Set<StrategyOutDto> findAll() {
        return strategyRepository.findAll().stream().map(strategyDtoTransformer::toDto).collect(Collectors.toSet());
    }

}
